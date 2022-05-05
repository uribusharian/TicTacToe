public class Board {

    private char state[][] = {{ '_', '_', '_' },
                              { '_', '_', '_' },
                              { '_', '_', '_' }};

    public int move(int row, int col, char player) {
        if(this.state[row][col] == '_') {
            this.state[row][col] = player;
            return 1;
        }
        else {
            return 0;
        }
    }

    public char[][] getState() {
        return this.state;
    }

    public void draw() {
        for (int i = 0; i < 3; i++) {
            char[] row = this.state[i];

            for (int j = 0; j < 3; j++) {
                System.out.print(row[j]);

                if (j < 2) {
                    System.out.print("|");
                }
            }


            System.out.println();

            if (i < 2) {
                System.out.println( "-+-+-" );
            }
        }

        System.out.println();
    }
}
