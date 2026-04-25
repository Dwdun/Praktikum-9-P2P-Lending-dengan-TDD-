package com.p2p.domain;

import java.math.BigDecimal;

public class Borrower {
    private static final int CREDIT_SCORE_THRESHOLD = 600;
    // Status verifikasi KYC
    private boolean verified;

    // Nilai credit score borrower
    private int creditScore;

    // Constructor untuk inisialisasi data borrower
    public Borrower(boolean verified, int creditScore) {
        this.verified = verified;
        this.creditScore = creditScore;
    }

    // Getter untuk mengecek apakah borrower sudah verified
    public boolean isVerified() {
        return verified;
    }

    // Getter untuk mengambil credit score
    public int getCreditScore() {
        return creditScore;
    }

    //validasi borrower (logic dibungkus di domain)
    public boolean canApplyLoan() {
        return verified;
    }

    //validasi creditscore
    public boolean hasHighCreditScore(){
        return creditScore >= CREDIT_SCORE_THRESHOLD;
    }
}
