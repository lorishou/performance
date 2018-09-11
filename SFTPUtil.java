package com.mhc.gwsti.biz.utils;

import com.jcraft.jsch.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;
import java.util.Vector;

/**
 * SFTP工具类
 */
public class SFTPUtil {
    private static Logger logger = LoggerFactory.getLogger(SFTPUtil.class);

    private static ChannelSftp sftp;
    private static Session session;
    private static String username;
    private static String password;
    private static String privateKey;
    private static String host;
    private static int port;
    private static byte[] privateKeyByte;
    private static byte[] passwordByte;


    /**
     * 构造基于秘钥认证的sftp对象
     *
     * @param privateKey
     * @param username
     * @param password
     * @param host
     * @param port
     */
    public SFTPUtil(String privateKey, String username, String password, String host, int port) {
        this.privateKey = privateKey;
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
    }

    public SFTPUtil(byte[] privateKeyByte, String username, byte[] passwordByte, String host, int port) {
        this.privateKeyByte = privateKeyByte;
        this.username = username;
        this.passwordByte = passwordByte;
        this.host = host;
        this.port = port;
    }

    /**
     * 连接sftp服务器
     *
     * @throws JSchException
     */
    public void login() throws JSchException {
        try {
            JSch jsch = new JSch();
            if (privateKey != null) {
                jsch.addIdentity(privateKey, password);
            } else if (ArrayUtils.isNotEmpty(privateKeyByte)) {
                if (ArrayUtils.isNotEmpty(passwordByte)) {
                    jsch.addIdentity("", privateKeyByte, null, passwordByte);
                } else {
                    jsch.addIdentity("", privateKeyByte, null, null);
                }
            }
            session = jsch.getSession(username, host, port);
            if (password != null) {
                session.setPassword(password);
            }

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            logger.info("sftp login success");
        } catch (JSchException e) {
            logger.warn("SFTPUtil login exception, host = {}, port = {}, username = {}, password = {}, key = {}",
                    host, port, username, password, privateKey);
            throw e;
        }
    }

    /**
     * 关闭连接 server
     */
    public void logout() {
        if (sftp != null) {
            if (sftp.isConnected()) {
                sftp.disconnect();
            }
        }
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
            }
        }
        logger.info("sftp logout success");
    }

    /**
     * 列出目录下的文件
     *
     * @param directory
     * @return
     * @throws SftpException
     */
    public Vector<?> listFiles(String directory) throws SftpException {
        try {
            return sftp.ls(directory);
        } catch (SftpException e) {
            logger.warn("SFTPUtil listFiles exception, directory = {}", directory);
            throw e;
        }
    }

    /**
     * 重命名文件
     *
     * @param path
     * @param oldName
     * @param newName
     * @throws SftpException
     */
    public void renameFile(String path, String oldName, String newName) throws SftpException {
        try {
            sftp.cd(path);
            sftp.rename(oldName, newName);
            logger.info("sftp renameFile success");
        } catch (SftpException e) {
            logger.warn("SFTPUtil renameFile exception, path = {}, oldName = {}, newName = {}", path, oldName, newName);
            throw e;
        }
    }

    /**
     * 上传文件
     *
     * @param directory
     * @param name
     * @param content
     * @throws SftpException
     */
    public void upload(String directory, String name, byte[] content) throws SftpException {
        try {
            sftp.cd(directory);
        } catch (SftpException e) {
            sftp.mkdir(directory);
            sftp.cd(directory);
        }
        try {
            sftp.put(new ByteArrayInputStream(content), name);
            logger.info("sftp upload success");
        } catch (SftpException e) {
            logger.warn("SFTPUtil upload exception, directory = {}, name = {}", directory, name);
            throw e;
        }
    }

    /**
     * 下载文件
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件
     * @param saveFile     存在本地的路径
     * @throws SftpException
     * @throws FileNotFoundException
     * @throws Exception
     */
    public void download(String directory, String downloadFile, String saveFile) throws SftpException, FileNotFoundException {
        if (directory != null && !"".equals(directory)) {
            sftp.cd(directory);
        }
        File file = new File(saveFile);
        sftp.get(downloadFile, new FileOutputStream(file));
    }

    /**
     * 下载文件
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件名
     * @return 字节数组
     * @throws SftpException
     * @throws IOException
     * @throws Exception
     */
    public byte[] download(String directory, String downloadFile) throws SftpException, IOException {
        if (directory != null && !"".equals(directory)) {
            sftp.cd(directory);
        }
        InputStream is = sftp.get(downloadFile);

        byte[] fileData = IOUtils.toByteArray(is);

        return fileData;
    }
}

具体使用：
    1.获取sftp服务器host、userName、port、password、privateKey
    2.连接
    byte[] passwordArray = StringUtils.isBlank(password) ? null : password.getBytes();
    byte[] privateKeyArray = IOUtils.toByteArray(this.getClass().getResourceAsStream("/jd_test_id_rsa"));

    SFTPUtil sftpUtil = new SFTPUtil(privateKeyArray, userName, passwordArray, host, port);
    sftpUtil.login();
    //todo
   
    3.释放链接
     sftpUtil.logout();
