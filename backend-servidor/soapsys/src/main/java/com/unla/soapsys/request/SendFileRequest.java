package com.unla.soapsys.request;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SendFileRequest")
public class SendFileRequest {
    private byte[] fileContent; // Contenido del archivo
    private String fileName; // Nombre del archivo

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
