package com.keke.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;

import java.io.File;


public class ToMp4ListenerTest {

    private static Logger logger = LoggerFactory.getLogger(ToMp4ListenerTest.class);

    public int convertVideo(String input, String output){

        try
        {
            File source = new File(input);
            File target = new File(output);

            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("aac");
            VideoAttributes video = new VideoAttributes();
            video.setCodec("h264");
            //video.setBitRate(new Integer(160000));
            //video.setFrameRate(new Integer(30));
            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setOutputFormat("mp4");
            attrs.setAudioAttributes(audio);
            attrs.setVideoAttributes(video);
            //Encode
            Encoder encoder = new Encoder();
            encoder.encode(new MultimediaObject(source), target, attrs, new MyChanageEncoderProgressListener());
            return 1;
        } catch (Exception ex) {
            logger.error("ToMp4ListenerTest#main 异常", ex);
            return 0;
        }

    }

    public static void main(String[] args) {
        try {
            File source = new File("/Users/uu/Desktop/1/11111.FLV");
            File target = new File("/Users/uu/Desktop/1/11111.mp4");

            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("libvorbis");
            VideoAttributes video = new VideoAttributes();
            video.setCodec("mpeg4");
            video.setBitRate(new Integer(160000));
            video.setFrameRate(new Integer(30));
            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setOutputFormat("mp4");
            attrs.setAudioAttributes(audio);
            attrs.setVideoAttributes(video);
            //Encode
            Encoder encoder = new Encoder();
            encoder.encode(new MultimediaObject(source), target, attrs, new MyChanageEncoderProgressListener());

        } catch (Exception ex) {
            logger.error("ToMp4ListenerTest#main 异常", ex);
        }
    }
}

