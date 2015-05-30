package com.omnicrola.panoptes.data.fileIO;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

import com.omnicrola.panoptes.control.TimeblockSet;
import com.omnicrola.panoptes.data.IReadTimeblock;
import com.omnicrola.panoptes.data.TimeBlock;
import com.omnicrola.panoptes.data.TimeData;

public class FileDataWriterFunctionalTest {

    private TimeBlock createBlock(int i, int j, String project, String card) {
        TimeBlock timeBlock = new TimeBlock(i, j);
        timeBlock.setTimeData(new TimeData(project, card, "sdf"));
        return timeBlock;
    }

    @Test
    public void testMarshalling() throws Exception {
        IFileWriter fileDataWriter = new FileDataWriter();
        ArrayList<IReadTimeblock> row1 = new ArrayList<>();

        row1.add(createBlock(0, 0, "Wilmut", "111"));
        row1.add(new TimeBlock(0, 1));
        row1.add(new TimeBlock(0, 2));
        row1.add(createBlock(0, 3, "Wilmut", "222"));
        row1.add(createBlock(0, 4, "Wilmut", "ALG"));
        row1.add(new TimeBlock(0, 5));
        row1.add(new TimeBlock(1, 0));
        row1.add(new TimeBlock(1, 1));
        row1.add(createBlock(1, 2, "Tetris", "009"));
        row1.add(new TimeBlock(1, 3));

        TimeblockSet timeblockSet = new TimeblockSet(row1);
        fileDataWriter.writeDataToFile(new File("panoptesMarshalTest.xml"), null, timeblockSet);
    }
}
