public class CustomerDetails {
    private String name;
    private String mobileNo;
    private long aadharNo;
    private int id;
    private int bookedRoomNo;

    public CustomerDetails(String name, String mobileNo, long aadharNo, int id, int bookedRoomNo) {
        this.name = name;
        this.mobileNo = mobileNo;
        this.aadharNo = aadharNo;
        this.id = id;
        this.bookedRoomNo = bookedRoomNo;
    }

    public String getName() {
        return name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public long getAadharNo() {
        return aadharNo;
    }

    public int getId() {
        return id;
    }

    public int getBookedRoomNo() {
        return bookedRoomNo;
    }

    public void setBookedRoomNo(int bookedRoomNo) {
        this.bookedRoomNo = bookedRoomNo;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Mobile No: " + mobileNo + ", Aadhar No: " + aadharNo;
    }
}
