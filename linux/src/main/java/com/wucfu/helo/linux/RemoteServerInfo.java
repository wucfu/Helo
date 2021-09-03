package com.wucfu.helo.linux;

import com.jcraft.jsch.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author wucfu
 * @description
 * @date 2021-03-22
 */
public class RemoteServerInfo {
    /**
     * 远程主机的ip地址
     */
    private String ip;

    /**
     * 远程主机登录用户名
     */
    private String username;

    /**
     * 远程主机的登录密码
     */
    private String password;

    private String identity = "~/.ssh/id_rsa";
    private String passphrase = "";

    /**
     * 设置ssh连接的远程端口
     */
    public static final int DEFAULT_SSH_PORT = 22;

    /**
     * 保存输出内容的容器
     */
    private ArrayList<String> stdout;

    /**
     * 初始化登录信息
     *
     * @param ip
     * @param username
     * @param password
     */
    public RemoteServerInfo(final String ip, final String username, final String password) {
        this.ip = ip;
        this.username = username;
        this.password = password;
        stdout = new ArrayList<String>();
    }

    /**
     * 执行shell命令
     *
     * @param command
     * @return
     */
    public int execute(final String command) {

        //   int returnCode = -1;
        JSch jsch = new JSch();

        try {
            /**
             * 创建session并且打开连接，因为创建session之后要主动打开连接
             */
            Session session = jsch.getSession(username, ip, DEFAULT_SSH_PORT);
            session.setPassword(password);
            //修改服务器/etc/ssh/sshd_config 中 GSSAPIAuthentication的值yes为no，解决用户不能远程登录
            session.setConfig("userauth.gssapi-with-mic", "no");
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            //打开通道，设置通道类型，和执行的命令
            Channel channel = session.openChannel("exec");
            ChannelExec channelExec = (ChannelExec) channel;
            channelExec.setCommand(command);

            channelExec.setInputStream(null);
            BufferedReader input = new BufferedReader(new InputStreamReader
                    (channelExec.getInputStream()));

            channelExec.connect();
            System.out.println("The remote command is :" + command);

            /*Field unsafeField = Unsafe.class.getDeclaredFields()[0];
            unsafeField.setAccessible(true);
            Unsafe unsafe = (Unsafe) unsafeField.get(null);
            unsafe.fullFence();*/

            // 接收远程服务器执行命令的结果
            String line;
            while ((line = input.readLine()) != null) {
                stdout.add(line);
            }
            // 得到returnCode
            int returnCode = -1;
            if (channelExec.isClosed()) {
                returnCode = channelExec.getExitStatus();
            }
            input.close();

            // 关闭通道
            channelExec.disconnect();
            //关闭session
            session.disconnect();
            return returnCode;

        } catch (JSchException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        //return returnCode;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static int getDefaultSshPort() {
        return DEFAULT_SSH_PORT;
    }

    public ArrayList<String> getStdout() {
        return stdout;
    }

    public void setStdout(ArrayList<String> stdout) {
        this.stdout = stdout;
    }
}
