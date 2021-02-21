package com.zzj.im.client.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.*;

import java.net.URI;

public class WebSocketClient {
    private URI uri;

    private Bootstrap bootstrap;
    private EventLoopGroup group;

    private ChannelPromise channelPromise;
    private Channel channel;

    public WebSocketClient(final URI uri){
        this.uri = uri;
        this.init();
    }

    public void connect(){
        try{
            System.out.println("正在与服务器建立连接...");
            channel = bootstrap.connect(uri.getHost(), uri.getPort()).sync().channel();
            channelPromise.sync();
            System.out.println("连接成功");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private void init(){
        bootstrap = new Bootstrap();
        //TCP优化
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        //建立连接的线程池
        group = new NioEventLoopGroup();
        bootstrap.group(group).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        //通用参数设置
                        ChannelPipeline pipeline = channel.pipeline();
                        //Http编解码
                        pipeline.addLast(new HttpClientCodec());
                        pipeline.addLast(new HttpObjectAggregator(64*1024));
                        //我们自己的业务处理,客户端自己维护握手协议
                        pipeline.addLast(new WebSocketHandler( getHandshaker(uri)));

                    }
                    //websocket握手协议
                    private WebSocketClientHandshaker getHandshaker(final URI uri){
                        return WebSocketClientHandshakerFactory.newHandshaker(uri,
                                WebSocketVersion.V13,null,false,null);
                    }
                });
    }
    private class WebSocketHandler extends SimpleChannelInboundHandler<Object>{
        //握手包
        private WebSocketClientHandshaker handshaker;

        public WebSocketHandler(final WebSocketClientHandshaker handshaker){
            this.handshaker = handshaker;
        }

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            channelPromise = ctx.newPromise();
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            this.handshaker.handshake(ctx.channel());
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object o) throws Exception {
            System.out.println("receive data: "+ o + " from address: "+ ctx.channel().remoteAddress());
            if(!handshaker.isHandshakeComplete()){
                try{
                    handshaker.finishHandshake(ctx.channel(), (FullHttpResponse) o);
                    channelPromise.setSuccess();
                    System.out.println("handshake success!");
                }catch(WebSocketHandshakeException e){
                    e.printStackTrace();
                    channelPromise.setFailure(e);
                }
                return ;
            }
            if(!(o instanceof TextWebSocketFrame)){
                System.out.println("receive error type message. o "+o);
                return;
            }
            TextWebSocketFrame request = (TextWebSocketFrame) o ;
            System.out.println("receive data: "+ request.text()+ " from address: "+ctx.channel().remoteAddress());
        }
    }
}
