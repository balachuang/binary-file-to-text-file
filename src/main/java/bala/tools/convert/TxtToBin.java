package bala.tools.convert;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.nio.ByteBuffer;

import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class TxtToBin
{
	public void run(String txtFilePath, String binFilePath)
	{

		// read textfile --> convert from BinToTxt, shouldn't containt line break.
		logger.info("Read text file: {}", txtFilePath);
		StringBuffer sbf = new StringBuffer();
		try (FileReader reader = new FileReader(txtFilePath))
		{
			BufferedReader br = new BufferedReader(reader);
			String line = br.readLine();
			while (line != null) {
				sbf.append(line);
				line = br.readLine();
			}
		} catch(Exception ex) {
			logger.error("Read text file ERROR: {}", ex);
		}

		// pre-check file length
		String txtCont = sbf.toString();
		if (txtCont.length() % 3 != 0)
		{
			logger.error("Wrong text file length ({}), should be multiples of 3.", txtCont.length());
			return;
		}

		// string to byte array
		ByteBuffer bbf = ByteBuffer.allocate(txtCont.length() / 3);
		for (int n=0; n<txtCont.length(); n+=3)
		{
			int num = Integer.parseInt(txtCont.substring(n, n+3)) - 128;
			if ((num < -128) || (num > 127))
			{
				logger.error("Wrong text file content ({}), all number should be in range of byte (-128 ~ 127).", num);
				return;
			}
			bbf.put((byte)num);
		}

		// write binary file
		logger.info("Write binary file: {}", binFilePath);
		byte[] content = bbf.array();
		try(FileOutputStream fos = new FileOutputStream(binFilePath))
		{
			fos.write(content, 0, content.length);
			fos.flush();
			fos.close();
		} catch (Exception ex) {
			logger.error("Write binary file ERROR: {}", ex);
		}

		logger.info("Convert done.");
	}
}
