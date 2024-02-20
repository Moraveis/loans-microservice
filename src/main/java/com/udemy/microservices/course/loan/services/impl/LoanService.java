package com.udemy.microservices.course.loan.services.impl;

import com.udemy.microservices.course.loan.constants.LoanConstants;
import com.udemy.microservices.course.loan.dtos.LoanDto;
import com.udemy.microservices.course.loan.entities.Loan;
import com.udemy.microservices.course.loan.exceptions.LoanAlreadyExistsException;
import com.udemy.microservices.course.loan.exceptions.ResourceNotFoundException;
import com.udemy.microservices.course.loan.mappers.LoanMapper;
import com.udemy.microservices.course.loan.repositories.LoanRepository;
import com.udemy.microservices.course.loan.services.ILoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class LoanService implements ILoanService {
    private final LoanRepository LoanRepository;

    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loan> optionalLoan = LoanRepository.findByMobileNumber(mobileNumber);
        if (optionalLoan.isPresent()) {
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber " + mobileNumber);
        }
        LoanRepository.save(createNewLoan(mobileNumber));
    }

    private Loan createNewLoan(String mobileNumber) {
        Loan newLoan = new Loan();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoanConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoanConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoanConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }

    @Override
    public LoanDto fetchLoan(String mobileNumber) {
        Loan Loan = LoanRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        return LoanMapper.mapToLoanDto(Loan, new LoanDto());
    }

    @Override
    public boolean updateLoan(LoanDto LoanDto) {
        Loan Loan = LoanRepository.findByLoanNumber(LoanDto.getLoanNumber()).orElseThrow(() -> new ResourceNotFoundException("Loan", "LoanNumber", LoanDto.getLoanNumber()));
        LoanMapper.mapToLoan(LoanDto, Loan);
        LoanRepository.save(Loan);
        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loan Loan = LoanRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        LoanRepository.deleteById(Loan.getLoanId());
        return true;
    }
}
