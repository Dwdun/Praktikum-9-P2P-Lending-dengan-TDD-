package com.p2p;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.p2p.domain.Borrower;
import com.p2p.domain.Loan;
import com.p2p.service.LoanService;

public class LoanServiceTest {
    private static final Logger logger = LogManager.getLogger(LoanServiceTest.class);

    @Test
    void shouldRejectLoanWhenBorrowerNotVerified() {
        logger.info("[TC-01] Tes Skenario Borrower meminjam tapi tidak terverifikasi");;
        // =====================================================
        // SCENARIO:
        // Borrower tidak terverifikasi (KYC = false)
        // Ketika borrower mengajukan pinjaman
        // Maka sistem harus menolak dengan melempar exception
        // =====================================================

        // =========================
        // Arrange (Initial Condition)
        // =========================
        // Borrower belum lolos proses KYC
        Borrower borrower = new Borrower(false, 700);
        logger.info("Inisial State:");
        logger.info("Borrower verified = false , credit score = 700");

        // Service untuk pengajuan loan
        LoanService loanService = new LoanService();

        // Jumlah pinjaman valid
        BigDecimal amount = BigDecimal.valueOf(1000);
        logger.info("Biaya yang akan dipinjam = 1000");

        // =========================
        // Act (Action)
        // =========================
        // Menangkap exception yang terjadi saat createLoan dipanggil, borrower mengajukan loan
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            loanService.createLoan(borrower, amount);
        });

        // =========================
        // Assert (Expected Result)
        // =========================
        assertEquals("Borrower not verified", exception.getMessage());
        logger.error("Exception berhasil ditangkap, Test [PASS]");
    }

    @Test
    void shouldRejectLoanWhenAmountIsZeroOrNegative(){
        logger.info("[TC-02] Tes Skenario Borrower terverifikasi tapi pinjaman tidak valid jumlahnya (kurang dari sama dengan nol)");;
        // =====================================================
        // SCENARIO:
        // Borrower terverifikasi (KYC = true)
        // Ketika borrower mengajukan pinjaman <= 0 amountnya
        // Maka sistem harus menolak dengan melempar exception
        // =====================================================

        // =========================
        // Arrange (Initial Condition)
        // =========================
        //Borrower sudah terverifikasi
        Borrower borrower = new Borrower(true, 700);
        logger.info("Inisial State:");
        logger.info("Borrower verified = true , credit score = 700");

        //service pengajuan Loan
        LoanService loanService = new LoanService();

        //jumlah pinjaman tidak valid (<= 0)
        BigDecimal amountZero = BigDecimal.valueOf(0);
        logger.info("Biaya yang akan dipinjam = 0");
        BigDecimal amountNegative = BigDecimal.valueOf(-67);
        logger.info("Biaya yang akan dipinjam = -67");

        // =========================
        // Act (Action)
        // =========================
        // Menangkap exception yang terjadi saat createloan dipanggil, amountnya 0 dan negatif
        Exception exceptionzero = assertThrows(IllegalArgumentException.class, () -> {
            loanService.createLoan(borrower, amountZero);
        });
        Exception exceptionnegative = assertThrows(IllegalArgumentException.class, () -> {
            loanService.createLoan(borrower, amountNegative);
        });

        // =========================
        // Assert (Expected Result)
        // =========================
        assertEquals("Amount is Too Low", exceptionzero.getMessage());
        logger.error("Exception Amount 0 berhasil ditangkap, Test [PASS]");
        assertEquals("Amount is Too Low", exceptionnegative.getMessage());
        logger.error("Exception Amount -67 berhasil ditangkap, Test [PASS]");
    }


    @Test
    void shouldApproveLoanWhenCreditScoreHigh(){
        logger.info("[TC-03] Tes Skenario Borrower terverifikasi, amount valid dan credit score tinggi");;
        // =====================================================
        // SCENARIO:
        // Borrower terverifikasi (KYC = true)
        // Ketika borrower credit scorenya tinggi diatas threshhold
        // Maka sistem harus approve peminjamannya
        // =====================================================

        // =========================
        // Arrange (Initial Condition)
        // =========================
        // Borrower sudah terverifikasi
        Borrower borrower = new Borrower(true, 1000);
        logger.info("Inisial State:");
        logger.info("Borrower verified = true , credit score = 1000");

        //service pengajuan Loan
        LoanService loanService = new LoanService();

        //jumlah pinjaman valid
        BigDecimal amount = BigDecimal.valueOf(800);
        logger.info("Biaya yang akan dipinjam = 800");

        // =========================
        // Act (Action)
        // =========================
        //meminjam uang memanggil createloan
        Loan loan = loanService.createLoan(borrower, amount);

        // =========================
        // Assert (Expected Result)
        // =========================
        assertEquals(Loan.Status.APPROVED, loan.getStatus());
        logger.info("Loan status APPROVED, Test [PASS]");
    }

    @Test
    void shouldRejectLoanWhenCreditScoreLow(){
       logger.info("[TC-04] Tes Skenario Borrower terverifikasi, amount valid tapi credit score rendah");;
        // =====================================================
        // SCENARIO:
        // Borrower terverifikasi (KYC = true)
        // Ketika borrower credit scorenya dibawah threshhold
        // Maka sistem harus reject peminjamannya
        // =====================================================

        // =========================
        // Arrange (Initial Condition)
        // =========================
        // Borrower sudah terverifikasi
        Borrower borrower = new Borrower(true, 300);
        logger.info("Inisial State:");
        logger.info("Borrower verified = true , credit score = 300");

        //service pengajuan Loan
        LoanService loanService = new LoanService();

        //jumlah pinjaman valid
        BigDecimal amount = BigDecimal.valueOf(800);
        logger.info("Biaya yang akan dipinjam = 800");

        // =========================
        // Act (Action)
        // =========================
        //meminjam uang memanggil createloan
        Loan loan = loanService.createLoan(borrower, amount);

        // =========================
        // Assert (Expected Result)
        // =========================
        assertEquals(Loan.Status.REJECTED, loan.getStatus());
        logger.info("Loan status REJECTED, Test [PASS]");
    }
}
