package honeyarcade.owner.ad.pro;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Setter
@Getter
@Slf4j
@ToString
public class OwnerAdProVO {

	private Integer	event_seq;
	private String	event_title;
	private Integer	event_price;
	private	Integer	event_sort;
	private String	event_text;
}
