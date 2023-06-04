package enumeration;

public enum Resolution {
	r1280X720("1280x720"),
	r1280x768("1280x768"),
	r1280x800("1280x800"),
	r1280x960("1280x960"),
	r1280x1024("1280x1024"),
	r1360x768("1360x768"),
	r1366x768("1366x768"),
	r1400x1050("1400x1050"),
	r1440x900("1440x900"),
	r1600x900("1600x900"),
	r1680x1050("1680x1050"),
	r1920X1080("1920x1080");
	
	private String resolution;

    private Resolution(String resolution) {
        this.resolution = resolution;
    }

	public String getResolution() {return resolution;}
	public void setResolution(String resolution) {this.resolution = resolution;}
	
	@Override
	public String toString() {
		return resolution;
	}
}
