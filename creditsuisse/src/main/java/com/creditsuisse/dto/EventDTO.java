package com.creditsuisse.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
	@JsonProperty("id")
	private String id;
	@JsonProperty("host")
	private String host;
	@JsonProperty("type")
	private String type;
	@JsonProperty("state")
	private String state;
	@JsonProperty("timestamp")
	private Long timestamp;
	
	private Long start;
	private Long end;
}
