package com.lendico.annuity.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lendico.annuity.constants.RestPathURI;
import com.lendico.annuity.dto.Annuity;
import com.lendico.annuity.dto.AnnuityRequest;
import com.lendico.annuity.service.AnnuityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * Annuity Controller Class
 * 
 * @author Srikanth
 *
 */
@Slf4j
@RestController
@RequestMapping(value = RestPathURI.CONTEXT_PATH)
@Api(value = "Annuity")
public class AnnuityController {

	@Autowired
	private AnnuityService annuityService;

	@PostMapping(
			path = RestPathURI.GENERATE_PLAN, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(tags = "Annuity Plan Generator", value = "In order to inform borrowers about the final repayment schedule, we need to have pre-calculated repayment plans throughout the lifetime of a loan.", notes = "The goal is to calculate a repayment plan for an annuity loan. Therefore the amount that the borrower has to pay back every month, consisting of principal "
			+ "and interest repayments, does not\r\n" + "change (the last installment might be an exception).")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK - Returns Response"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 403, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error / Technical Error"),
			@ApiResponse(code = 503, message = "Services Unavailable / Time out") })
	public Annuity generatePlan(@Valid @RequestBody AnnuityRequest annuityRequest) {
		log.info("Entry AnnuityController:generatePlan {}", annuityRequest.toString());
		return annuityService.getRepaymentPlan(annuityRequest);

	}

}
