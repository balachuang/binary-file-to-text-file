package bala.tools.convert;

// import java.io.BufferedWriter;
// import java.io.File;
// import java.io.FileInputStream;
// import java.io.FileWriter;
// import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
@Order(1)
public class Converter implements ApplicationRunner
{
	@Autowired
	BinToTxt b2t;

	@Autowired
	TxtToBin t2b;

	public void run(ApplicationArguments args)
	{
		String runType = "";
		String binFileName = "";
		String txtFileName = "";

		String[] argAry = args.getSourceArgs();
		for (int n=0; n<argAry.length; ++n)
		{
			if ("-c".equals(argAry[n]) && (n+1 < argAry.length)) runType = argAry[n+1];
			if ("-b".equals(argAry[n]) && (n+1 < argAry.length)) binFileName = argAry[n+1];
			if ("-t".equals(argAry[n]) && (n+1 < argAry.length)) txtFileName = argAry[n+1];
		}

		if (StringUtils.hasText(runType))
		{
			switch(runType)
			{
				case "b2t":
					b2t.run(binFileName, txtFileName);
					return;
				case "t2b":
					t2b.run(txtFileName, binFileName);
					return;
			}
		}

		showUsageMsg();
	}

	private void showUsageMsg()
	{
		logger.info("Use following format to identify binary file name:");
		logger.info("Ex: java -jar bin-to-txt.jar -c convert-type -b binary_file_path -t text_file_path");
		logger.info("    convert-type: b2t - convert binary file to text file");
		logger.info("                  t2b - convert text file to binary file");
	}
}
