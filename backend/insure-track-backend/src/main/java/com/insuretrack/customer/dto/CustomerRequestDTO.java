package com.insuretrack.customer.dto;

import com.insuretrack.common.enums.Segment;
import com.insuretrack.common.enums.Status;

import java.time.LocalDate;

public record CustomerRequestDTO(String name, LocalDate dob, String contactInfo, Segment segment, Status status) {
}
