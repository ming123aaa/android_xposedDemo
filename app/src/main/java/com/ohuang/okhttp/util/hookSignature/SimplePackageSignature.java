package com.ohuang.okhttp.util.hookSignature;

import com.ohuang.okhttp.util.PackageSignature;

import java.util.List;

public class SimplePackageSignature extends PackageSignature {
    String packageName;
    byte[] bytes;
    public SimplePackageSignature(String packageName,byte[] bytes) {
        this.packageName=packageName;
        this.bytes=bytes;
    }

    @Override
    public String packageName() {
        return packageName;
    }

    @Override
    public byte[] getDefaultSignature() {
        return bytes;
    }

    @Override
    public void initSignatures(List<byte[]> signatures) {

    }
}