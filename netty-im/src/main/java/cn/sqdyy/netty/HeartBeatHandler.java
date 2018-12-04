package cn.sqdyy.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="blog.sqdyy.cn">神奇的鸭鸭</a>
 * @ClassName HeartBeatHandler
 * @Description 用于检测 channel 的心跳 handler
 * 继承 ChannelInboundHandlerAdapter，从而不需要实现 channelRead0 方法
 * @Date: 2018/11/30 11:08
 * @Version 1.0.0
 */
@Slf4j
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 判断 evt 是否是 IdleStateEvent（用于触发用户事件，包含 读空闲/写空闲/读写空闲 ）
        if(evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent)evt;		// 强制类型转换
            if (event.state() == IdleState.READER_IDLE) {
                log.info("进入读空闲...");
            } else if (event.state() == IdleState.WRITER_IDLE) {
                log.info("进入写空闲...");
            } else if (event.state() == IdleState.ALL_IDLE) {
                log.info("channel 关闭前，users 的数量为：" + ChatHandler.users.size());
                Channel channel = ctx.channel();
                // 关闭无用的 channel，以防资源浪费
                channel.close();
                log.info("channel 关闭后，users 的数量为：" + ChatHandler.users.size());
            }
        }
    }
}
