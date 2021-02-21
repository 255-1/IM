package com.zzj.im.server.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.Setter;
import org.springframework.stereotype.Service;

/*
* 基于Netty开发WebSocket服务端
* */
public class WebSocketServer {
    @Setter
    private String contexPath;
    /*
    * 起动器
    * */
    private ServerBootstrap bootstrap;
    /*
    * 接受业务
    * */
    private EventLoopGroup boss;
    /*
    * 处理业务
    * */
    private EventLoopGroup worker;

    public WebSocketServer(final String contextPath){
        this.contexPath = contextPath;
    }
    public void start(final short port){
        this.init();
        try {
            //等待连接
            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("server started. listen on: "+port+"!");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
    * 初始化Netty参数
    * */
    private void init(){

        bootstrap = new ServerBootstrap();
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024);

        boss = new NioEventLoopGroup();
        worker = new NioEventLoopGroup(5);
        //接受连接的线程
        bootstrap.group(boss,worker)
                .channel(NioServerSocketChannel.class)//处理方式
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    /*
                    * 责任链真正处理的业务,FCFS
                    * */
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        //通用
                        pipeline.addLast(new HttpServerCodec());//Http编码
                        pipeline.addLast(new ChunkedWriteHandler());//分片
                        pipeline.addLast((new HttpObjectAggregator(64 *1024)));//分片大小
                        pipeline.addLast(new WebSocketServerProtocolHandler(contexPath));//Netty处理WebSocket握手协议
                        //我们自己的业务处理
                        pipeline.addLast(new WebSocketHandler());
                    }
                });
    }
    //在这个类中真正处理接受到的业务数据
    private class WebSocketHandler extends SimpleChannelInboundHandler<Object>{

        //可以查看连接是什么时候建立的
        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            System.out.println("connected from adress: "+ctx.channel().remoteAddress());
        }

        //连接主动断开
        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
            System.out.println("removed from adress: " + ctx.channel().remoteAddress());
        }

        //数据连接
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object o) throws Exception {
            System.out.println("receive data: " + o + " from address: "+ctx.channel().remoteAddress());
            if(!(o instanceof TextWebSocketFrame)){
                System.out.println("receive error type message. o" + o);
                return ;
            }
            TextWebSocketFrame request = (TextWebSocketFrame)o;
            System.out.println("received text: "+request.text());
            //TODO:业务逻辑
            ctx.writeAndFlush(new TextWebSocketFrame("服务端返回: "+request.text()));
        }
    }
}
