package sparta.a08.trello.common.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@Configuration
public class JpaAuditingConfig {
}

//BaseEntity의 createdAt, lastModifiedAt 필드에 값을 자동으로 넣어주는 Auditing 기능을 활성화하기 위해 config 추가
//Main Application 파일에 선언하게되면 테스트 과정에서 버그가 발생하기 때문에 별도의 config 파일을 생성하여 사용