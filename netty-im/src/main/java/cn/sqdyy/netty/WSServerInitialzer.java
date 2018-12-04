package cn.sqdyy.netty;

import cn.sqdyy.utils.SslUtil;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import java.io.File;
import java.io.InputStream;


/**
 * @author <a href="blog.sqdyy.cn">神奇的鸭鸭</a>
 * @ClassName WSServerInitialzer
 * @Description WSServerInitialzer
 * WebSocketServerProtocolHandler 会帮你处理一些繁重的复杂的事
 * 会帮你处理握手动作： handshaking（close, ping, pong） ping + pong = 心跳
 * 对于 websocket 来讲，都是以 frames 进行传输的，不同的数据类型对应的 frames 也不同
 * @Date: 2018/11/30 10:57
 * @Version 1.0.0
 */
public class WSServerInitialzer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        SSLContext sslContext = SslUtil.createSSLContext("JKS",new File("/home/netty-im/netty-im.keystore"),"szfa9860");
        //SSLEngine 此类允许使用ssl安全套接层协议进行安全通信
        SSLEngine engine = sslContext.createSSLEngine();
        engine.setUseClientMode(false);
        ch.pipeline()
                .addLast(new SslHandler(engine)) // WSS
                .addLast(new HttpServerCodec()) // websocket 基于 http 协议，所以要有 http 编解码器
                .addLast(new ChunkedWriteHandler()) // 对写大数据流的支持
                .addLast(new HttpObjectAggregator(1024*64)) // 对 httpMessage 进行聚合，聚合成 FullHttpRequest 或 FullHttpResponse
                // ====================== 以上是用于支持http协议    ======================
                // ====================== 增加心跳支持 start    ======================
                // 针对客户端，如果在 1 分钟时没有向服务端发送读写心跳(ALL)，则主动断开
                // 如果是读空闲或者写空闲，不处理
                .addLast(new IdleStateHandler(8, 10, 12))
                // 自定义的空闲状态检测
                .addLast(new HeartBeatHandler())
                // ====================== 增加心跳支持 end    ======================
                // websocket 服务器处理的协议，用于指定给客户端连接访问的路由 : /ws
                .addLast(new WebSocketServerProtocolHandler("/ws"))
                // 自定义的handler
                .addLast(new ChatHandler());
    }
}
