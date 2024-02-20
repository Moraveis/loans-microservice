package com.udemy.microservices.course.loan.services;

import com.udemy.microservices.course.loan.dtos.LoanDto;

public interface ILoanService {

    void createLoan(String mobileNumber);

    LoanDto fetchLoan(String mobileNumber);

    boolean updateLoan(LoanDto loanDto);

    boolean deleteLoan(String mobileNumber);
}
