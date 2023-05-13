package in.tube.model;

import lombok.Data;

@Data
public class CoronaModel {

	private String country;
	private String state;
	private Integer latestCount;
	private Integer diffFromPrev;
}
