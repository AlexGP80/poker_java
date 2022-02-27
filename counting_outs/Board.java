public class Board {
	public static final int STAGE_PRE_FLOP = 0;
	public static final int STAGE_FLOP = 3;
	public static final int STAGE_TURN = 4;
	public static final int STAGE_RIVER = 5;

	private Card flop1, flop2, flop3, turn, river;
	private int stage; // 0: pre-flop; 3: flop; 4: turn; 5: river;

	public Board() {
		this.flop1 = null;
		this.flop2 = null;
		this.flop3 = null;
		this.turn = null;
		this.river = null;
		stage = 0;
	}

	public String getBoardText() {
		String boardText = "";
		if (stage == STAGE_PRE_FLOP) return boardText;
		boardText += flop1.getText() + flop2.getText() + flop3.getText();
		if (stage == STAGE_FLOP) return boardText;
		boardText += turn.getText();
		if (stage == STAGE_TURN) return boardText;
		boardText += river.getText();
		return boardText;
	}

	public Card getFlop1() {
		return flop1;
	}

	public Card getFlop2() {
		return flop2;
	}

	public Card getFlop3() {
		return flop3;
	}

	public Card getTurn() {
		return turn;
	}

	public Card getRiver() {
		return river;
	}

	public int getStage() {
		return stage;
	}

	public boolean backToStage(int stage) {
		if (this.stage < stage) return false;
		this.stage = stage;
		if (stage == STAGE_PRE_FLOP) {
			this.flop1=null;
			this.flop2=null;
			this.flop3=null;
			this.turn=null;
			this.river=null;
		} else if (stage == STAGE_FLOP) {
			this.turn=null;
			this.river=null;			
		} else if (stage == STAGE_TURN) {
			this.river=null;
		} else {
			return false;
		}
		return true;
	}

	public boolean setFlop(Card flop1, Card flop2, Card flop3) {
		if (stage != STAGE_PRE_FLOP) return false;
		this.flop1 = flop1;
		this.flop2 = flop2;
		this.flop3 = flop3;
		this.stage = STAGE_FLOP;
		return true;
	}

	public boolean replaceTurn(Card turn) {
		if (stage < STAGE_TURN) return false;
		this.turn = turn;
		return true;
	}

	public boolean setTurn(Card turn) {
		if (stage != STAGE_FLOP) return false;
		this.turn = turn;
		this.stage = STAGE_TURN;
		return true;
	}

	public boolean replaceRiver(Card river) {
		if (stage < STAGE_RIVER) return false;
		this.river = river;
		return true;

	}

	public boolean setRiver(Card river) {
		if (stage != STAGE_TURN) return false;
		this.river = river;
		this.stage = STAGE_RIVER;
		return true;
	}

}