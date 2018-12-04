package cn.sqdyy.netty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="blog.sqdyy.cn">神奇的鸭鸭</a>
 * @ClassName DataContent
 * @Description websocket 数据连接结构
 * @Date: 2018/11/30 17:17
 * @Version 1.0.0
 */
@ApiModel(value = "websocket 数据发送结构", description = "websocket 数据连接结构")
@Data
public class DataContent implements Serializable {
    private static final long serialVersionUID = 8021381444738260454L;
    @ApiModelProperty(value = "动作类型", name = "action")
    private Integer action;
    @ApiModelProperty(value = "用户消息", name = "chatMsg")
    private ChatMsg chatMsg;
    @ApiModelProperty(value = "扩展字段", name = "extand")
    private String extand;
}
