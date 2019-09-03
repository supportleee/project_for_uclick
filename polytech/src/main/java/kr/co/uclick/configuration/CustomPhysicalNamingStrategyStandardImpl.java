package kr.co.uclick.configuration;

import java.util.Locale;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

// DB에 Table 생성 시 이름 규칙을 지정한 클래스
public class CustomPhysicalNamingStrategyStandardImpl extends PhysicalNamingStrategyStandardImpl {
	
	// 부모 클래스인 PhysicalNamingStrategyStandardImpl클래스가 Serializable 클래스를 구현하고 있어서 serialVersionUID를 지정해줌
	private static final long serialVersionUID = -6972754781554141247L;

	// Table 이름을 지정하는 메소드
	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
		// 파라미터로 들어온 name을 가진 Identifier객체 return
		return new Identifier(addUnderscores(name.getText()), name.isQuoted());
	}

	// Column 이름을 지정하는 메소드
	@Override
	public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
		// 파라미터로 들어온 name을 가진 Identifier객체 return
		return new Identifier(addUnderscores(name.getText()), name.isQuoted());
	}

	// 이름 생성 시 스트링을 변환해 줌
	protected static String addUnderscores(String name) {
		// 들어온 String의 '.'를 '_'로 변환
		final StringBuilder buf = new StringBuilder(name.replace('.', '_'));
		for (int i = 1; i < buf.length() - 1; i++) {
			// 이름이 카멜 표기 방식일 경우 단어 사이에 _를 넣어줌
			if (Character.isLowerCase(buf.charAt(i - 1)) && Character.isUpperCase(buf.charAt(i))
					&& Character.isLowerCase(buf.charAt(i + 1))) {
				buf.insert(i++, '_');
			}
		}
		// 변환된 이름을 소문자화해서 return
		return buf.toString().toLowerCase(Locale.ROOT);
	}
}
