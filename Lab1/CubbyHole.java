package Lab1;

class CubbyHole {
		
	private int contents;
	private boolean available = false;
	
	public synchronized int get() {
		while(available == false) {
			try { wait();
			} catch (InterruptedException e) {
			}
		}
		available = false;
		notify();
		return contents;
	}
	
	public synchronized void put(int value) {
		while(available == true) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		contents = value; 
		available = true; notify();
	}
}
