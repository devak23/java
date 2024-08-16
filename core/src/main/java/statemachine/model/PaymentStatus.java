package statemachine.model;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    DRAFT(0) {
        @Override
        public PaymentStatus nextState() {
            return INITIATED;
        }

        public String toString() {
            return "The payment is created by the customer but still being edited";
        }
    },
    INITIATED (1) {
        @Override
        public PaymentStatus nextState() {
            return PENDING_APPROVAL;
        }

        public String toString() {
            return "The payment has been initiated by the customer";
        }
    },
    PENDING_APPROVAL (2) {
        @Override
        public PaymentStatus nextState() {
            return APPROVED;
        }

        public String toString() {
            return "The payment is pending approval by the senior officer";
        }
    },
    REJECTED (3) {
        @Override
        public PaymentStatus nextState() {
            return null;
        }
        public String toString() {
            return "The payment has been rejected by the bank";
        }
    },
    APPROVED (4) {
        @Override
        public PaymentStatus nextState() {
            return SUBMITTED;
        }

        public String toString() {
            return "The payment is approved by the senior officer";
        }
    },
    SUBMITTED (5) {
        @Override
        public PaymentStatus nextState() {
            return COMPLETED;
        }

        public String toString() {
            return "The payment is submitted for further processing";
        }
    },
    COMPLETED (6) {
        @Override
        public PaymentStatus nextState() {
            return this;
        }

        public String toString() {
            return "The payment processing is completed.";
        }
    };

    private final int order;

    PaymentStatus(int order) {
        this.order = order;
    }

    public abstract PaymentStatus nextState();
}
