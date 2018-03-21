package org.asocframework.dds.test.example;

import org.asocframework.dds.sequence.SequenceFactory;
import org.asocframework.dds.test.dal.mapper.AssetSerialMapper;
import org.asocframework.dds.test.dal.model.AssetSerial;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dhj
 * @version $Id: DtsExample ,v 1.0 2017/7/12 0012 dhj Exp $
 * @name
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class DtsExample {

    @Resource
    private AssetSerialMapper assetSerialMapper;

    @Resource
    private SequenceFactory sequenceFactory;

    @Test
    public void  simple() throws InterruptedException {

        AssetSerial assetSerial = assetSerialMapper.find("1111111");
        System.out.println(assetSerial.getTxId());

        Thread.sleep(10000L);
    }

    @Test
    public void sequenceTest(){

        for(int i = 0 ;i<200;i++){
            int value = sequenceFactory.next("serial","1","01");
            System.out.println(value);
        }

    }

}
