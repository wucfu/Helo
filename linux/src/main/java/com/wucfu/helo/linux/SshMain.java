package com.wucfu.helo.linux;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.wucfu.helo.linux.RemoteServerInfo.DEFAULT_SSH_PORT;

/**
 * @author wucfu
 * @description
 * @date 2021-03-22
 */
public class SshMain {
    public static void main(String[] args) throws IOException {
        RemoteServerInfo rsi = new RemoteServerInfo("127.0.0.1","root", "root");
        rsi.execute("sh x.sh");
        rsi.getStdout().forEach(System.out::println);

    }

}
