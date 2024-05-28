package org.ict.boot_react.common;

import lombok.*;
import org.springframework.stereotype.Component;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Component
public class SearchDate {
	private Date begin;
	private Date end;	

}
