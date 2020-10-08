package com.yangml.utils;

import com.yangml.pojo.FastDFSFile;
import lombok.extern.slf4j.Slf4j;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
@Slf4j
public class FastDFSClient {
//    private static TrackerClient trackerClient =null;
//    private static TrackerServer trackerServer = null;
//    private static StorageServer storageServer=null;
//    private static StorageClient storageClient=null;
//    static {
//        try {
//            String filePath = new ClassPathResource("fdfs_client.conf").getFile().getAbsolutePath();;
//            ClientGlobal.init(filePath);
//            trackerClient = new TrackerClient();
////            trackerClient.getTrackerServer()
//            trackerServer = trackerClient.getTrackerServer();
//            storageServer = trackerClient.getStoreStorage(trackerServer);
//        } catch (Exception e) {
//            log.error("FastDFS Client Init Fail!",e);
//        }
//    }
//    public static String[] upload(FastDFSFile file) {
//        log.info("File Name: " + file.getName() + "File Length:" + file.getContent().length);
//
//        NameValuePair[] meta_list = new NameValuePair[1];
//        meta_list[0] = new NameValuePair("author", file.getAuthor());
//
//        long startTime = System.currentTimeMillis();
//        String[] uploadResults = null;
//        try {
//            storageClient = new StorageClient(trackerServer, storageServer);
//            uploadResults = storageClient.upload_file(file.getContent(), file.getExt(), meta_list);
//        } catch (IOException e) {
//            log.error("IO Exception when uploadind the file:" + file.getName(), e);
//        } catch (Exception e) {
//            log.error("Non IO Exception when uploadind the file:" + file.getName(), e);
//        }
//        log.info("upload_file time used:" + (System.currentTimeMillis() - startTime) + " ms");
//
//        if (uploadResults == null) {
//            log.error("upload file fail, error code:" + storageClient.getErrorCode());
//        }
//        String groupName = uploadResults[0];
//        String remoteFileName = uploadResults[1];
//
//        log.info("upload file successfully!!!" + "group_name:" + groupName + ", remoteFileName:" + " " + remoteFileName);
//        return uploadResults;
//    }
//    public static FileInfo getFile(String groupName, String remoteFileName) {
//        try {
//            storageClient = new StorageClient(trackerServer, storageServer);
//            return storageClient.get_file_info(groupName, remoteFileName);
//        } catch (IOException e) {
//            log.error("IO Exception: Get File from Fast DFS failed", e);
//        } catch (Exception e) {
//            log.error("Non IO Exception: Get File from Fast DFS failed", e);
//        }
//        return null;
//    }
//    public static InputStream downFile(String groupName, String remoteFileName) {
//        try {
//            storageClient = new StorageClient(trackerServer, storageServer);
//            byte[] fileByte = storageClient.download_file(groupName, remoteFileName);
//            InputStream ins = new ByteArrayInputStream(fileByte);
//            return ins;
//        } catch (IOException e) {
//            log.error("IO Exception: Get File from Fast DFS failed", e);
//        } catch (Exception e) {
//            log.error("Non IO Exception: Get File from Fast DFS failed", e);
//        }
//        return null;
//    }
//    public static void deleteFile(String groupName, String remoteFileName)
//            throws Exception {
//        storageClient = new StorageClient(trackerServer, storageServer);
//        int i = storageClient.delete_file(groupName, remoteFileName);
//        log.info("delete file successfully!!!" + i);
//    }
    /***
     * 初始化tracker信息
     */
    static {
        try {
            //获取tracker的配置文件fdfs_client.conf的位置
            String filePath = new ClassPathResource("fdfs_client.conf").getPath();
            //加载tracker配置信息
            ClientGlobal.init(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /****
     * 文件上传
     * @param file : 要上传的文件信息封装->FastDFSFile
     * @return String[]
     *          1:文件上传所存储的组名
     *          2:文件存储路径
     */
    public static String[] upload(FastDFSFile file){
        //获取文件作者
        NameValuePair[] meta_list = new NameValuePair[1];
        meta_list[0] =new NameValuePair(file.getAuthor());

        /***
         * 文件上传后的返回值
         * uploadResults[0]:文件上传所存储的组名，例如:group1
         * uploadResults[1]:文件存储路径,例如：M00/00/00/wKjThF0DBzaAP23MAAXz2mMp9oM26.jpeg
         */
        String[] uploadResults = null;
        try {
            //获取StorageClient对象
            StorageClient storageClient = getStorageClient();
            /**执行文件上传
             *1 上传文件字节数组
             *2 文件的后缀名
             * 3 附加参数 例如拍摄场地：广州
             其中file.getExt在controller层就把后缀名加到FastDFSFile中了
             */
            uploadResults = storageClient.upload_file(file.getContent(), file.getExt(), meta_list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadResults;
    }


    /***
     * 获取文件信息
     * @param groupName:组名
     * @param remoteFileName：文件存储完整名
     */
    public static FileInfo getFile(String groupName,String remoteFileName){
        try {
            //获取StorageClient对象
            StorageClient storageClient = getStorageClient();
            //获取文件信息
            return storageClient.get_file_info(groupName,remoteFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 文件下载
     * @param groupName:组名
     * @param remoteFileName：文件存储完整名
     * @return
     */
    public static InputStream downFile(String groupName,String remoteFileName){
        try {
            //获取StorageClient
            StorageClient storageClient = getStorageClient();
            //通过StorageClient下载文件
            byte[] fileByte = storageClient.download_file(groupName, remoteFileName);
            //将字节数组转换成字节输入流
            return new ByteArrayInputStream(fileByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 文件删除实现
     * @param groupName:组名
     * @param remoteFileName：文件存储完整名
     */
    public static void deleteFile(String groupName,String remoteFileName){
        try {
            //获取StorageClient
            StorageClient storageClient = getStorageClient();
            //通过StorageClient删除文件
            storageClient.delete_file(groupName,remoteFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /***
     * 获取组信息
     * @param groupName :组名
     */
    public static StorageServer getStorages(String groupName){
        try {
            //创建TrackerClient对象
            TrackerClient trackerClient = new TrackerClient();
            //通过TrackerClient获取TrackerServer对象
            TrackerServer trackerServer = trackerClient.getConnection();
            //通过trackerClient获取Storage组信息
            return trackerClient.getStoreStorage(trackerServer,groupName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 根据文件组名和文件存储路径获取Storage服务的IP、端口信息
     * @param groupName :组名
     * @param remoteFileName ：文件存储完整名
     */
    public static ServerInfo[] getServerInfo(String groupName, String remoteFileName){
        try {
            //创建TrackerClient对象
            TrackerClient trackerClient = new TrackerClient();
            //通过TrackerClient获取TrackerServer对象
            TrackerServer trackerServer = trackerClient.getConnection();
            //获取服务信息
            return trackerClient.getFetchStorages(trackerServer,groupName,remoteFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 获取Tracker服务地址
     */
    public static String getTrackerUrl(){
        try {
            //创建TrackerClient对象
            TrackerClient trackerClient = new TrackerClient();
            //通过TrackerClient获取TrackerServer对象
            TrackerServer trackerServer = trackerClient.getConnection();
            //获取Tracker地址
            return "http://"+trackerServer.getInetSocketAddress().getHostString()+":"+ClientGlobal.getG_tracker_http_port();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 获取TrackerServer
     */
    public static TrackerServer getTrackerServer() throws Exception{
        //创建TrackerClient对象
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient获取TrackerServer对象
        TrackerServer trackerServer = trackerClient.getConnection();
        return trackerServer;
    }

    /***
     * 获取StorageClient
     * @return
     * @throws Exception
     */
    public static StorageClient getStorageClient() throws Exception{
        //获取TrackerServer
        TrackerServer trackerServer = getTrackerServer();
        //通过TrackerServer创建StorageClient
        StorageClient storageClient = new StorageClient(trackerServer,null);
        return storageClient;
    }
}
