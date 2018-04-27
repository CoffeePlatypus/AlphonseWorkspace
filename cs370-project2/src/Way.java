
public class Way {
	private boolean valid = false;
	private boolean dirty = false;
	private long tag;
	
	public Way(long tag) {
		this.tag=tag;
		valid=true;
	}
	
	public long getTag() {
		if(valid) {
			return tag;
		}else{
			return -1;
		}
	}
	
	public void update(){
		dirty=true;
	}
	
	public boolean isDirty() {
		return dirty;
	}

}
