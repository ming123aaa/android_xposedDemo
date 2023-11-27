package com.ohuang.okhttp.util;

import java.util.ArrayList;
import java.util.List;

public abstract class PackageSignature {

    public PackageSignature() {
        initSignatures(signatures);
    }

    public List<byte[]> signatures=new ArrayList<>();

    public abstract String  packageName();

    public abstract byte[] getDefaultSignature();

    public abstract void initSignatures(List<byte[]> signatures);

    public  byte[] getSignature(int get){
        if (signatures.size()==0||get<0){
            return getDefaultSignature();
        }
        if (signatures.size()>get){
            return signatures.get(get);
        }else{
            return signatures.get(signatures.size()-1);
        }

    }


}
