package com.keke.converter;

import com.liumapp.workable.converter.WorkableConverter;
import com.liumapp.workable.converter.core.ConvertPattern;
import com.liumapp.workable.converter.exceptions.ConvertFailedException;
import com.liumapp.workable.converter.factory.CommonConverterManager;
import com.liumapp.workable.converter.factory.ConvertPatternManager;
import org.jodconverter.DocumentConverter;
import org.jodconverter.JodConverter;
import org.jodconverter.document.DefaultDocumentFormatRegistry;
import org.jodconverter.office.LocalOfficeManager;
import org.jodconverter.office.OfficeException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class OpenOfficePDFConverter{

    public synchronized Boolean convert(String source, String target) throws OfficeException, ConvertFailedException {

        System.out.println(source);
        WorkableConverter converter = new WorkableConverter();//实例化的同时，初始化配置项，配置项的校验通过Decorator装饰

        ConvertPattern pattern = ConvertPatternManager.getInstance();
        pattern.fileToFile(source, target); //test.doc为待转换文件路径，result1.pdf为转换结果存储路径

        converter.setConverterType(CommonConverterManager.getInstance());//策略模式，后续实现了新的转换策略后，在此处更换，图片转换将考虑使用新的策略来完成
        boolean result = converter.convert(pattern.getParameter());
        return result;

    }
}
