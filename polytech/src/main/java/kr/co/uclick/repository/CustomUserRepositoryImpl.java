package kr.co.uclick.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomUserRepositoryImpl implements CustomUserRepository{

	private static final Logger logger = LoggerFactory.getLogger(CustomSampleRepositoryImpl.class);

	@Override
	public void logFindByName(String name) {
		// TODO Auto-generated method stub
		logger.debug("logFindByName : {}, {}", name, 1);
	}

}
