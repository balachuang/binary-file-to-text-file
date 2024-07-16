package bala.tools.convert;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class BinToTxt
{
	public void run(String binFilePath, String txtFilePath)
	{
		File myFile = new File(binFilePath);
		byte[] byteArray = new byte[(int) myFile.length()];

		// read binaryfile
		logger.info("Read binary file: {}", binFilePath);
		try (FileInputStream inputStream = new FileInputStream(myFile))
		{
			inputStream.read(byteArray);
		} catch (IOException ex) {
			logger.error("Read binary file ERROR: {}", ex);
			byteArray = null;
		}

		// write textfile
		logger.info("Write text file: {}", txtFilePath);
		BufferedWriter writer = null;
		try (FileWriter fw = new FileWriter(txtFilePath))
		{
			writer = new BufferedWriter(fw);
			for (byte ct : byteArray)
			{
				short ss = ct;
				String sss = String.format("%03d", ss + 128);
				writer.write(sss);
			}
			writer.close();
		} catch (Exception ex) {
			logger.error("Write text file ERROR: {}", ex);
		}

		logger.info("Convert done.");
	}
}
