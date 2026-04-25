package com.p2p.service;
import com.p2p.domain.*;
import java.math.BigDecimal;


public class LoanService {
    public Loan createLoan(Borrower borrower, BigDecimal amount) {
        // =========================
        // VALIDASI UTAMA (TC-01)
        // =========================
        // Jika borrower belum terverifikasi,
        // maka proses harus dihentikan

        //panggil method validateborrower
        validateBorrower(borrower);
        validateAmount(amount);

        // Membuat objek loan baru
        Loan loan = new Loan();
        // =========================
        // LOGIC SEDERHANA (sementara)
        // =========================
        // Jika credit score tinggi → APPROVED
        // Jika tidak → REJECTED
        evaluateLoan(borrower, loan);
        return loan;
    }

    //memisahkan method validasi dari createloan agar lebih fokus tasknya
    private void validateBorrower(Borrower borrower) {
        //service jadi orchestration aja , logic pindah ke domain
        if (!borrower.canApplyLoan()) {
            throw new IllegalArgumentException("Borrower not verified");
        }
    }

    //validasi amount (<= 0)
    private void validateAmount(BigDecimal amount){
        if(!Loan.isValidAmount(amount)){
            throw new IllegalArgumentException("Amount is Too Low");
        }
    }

    //validasi credit score dan evaluasi loan apakah diterima atau di tolak
    private void evaluateLoan(Borrower borrower, Loan loan){
        if (borrower.hasHighCreditScore()) {
            //enkapsulasi ga langsung di set untuk approve
            loan.approve();
        } else {
            //enkapsulasi ga langsung di set untuk rejected
            loan.reject();
        }
    }
}

