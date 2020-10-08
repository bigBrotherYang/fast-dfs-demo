package com.yangml.pojo;

import lombok.Data;

@Data

public class FastDFSFile {

    private String name;
    private byte[] content;
    private String ext;
    private String md5;
    private String author;

    public FastDFSFile(String fileName, byte[] file_buff, String ext) {
        this.name =fileName;
        this.content = file_buff;
        this.ext =ext;
    }
}
