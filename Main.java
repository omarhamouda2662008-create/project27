

public class Main {

    static int best = 0;
    static int bestBy = 0;
    static int checks = 0;

    static MySemaphore mutex = new MySemaphore(1);

    static class Teacher extends Thread {

        int id;

        public Teacher(int id) {
            this.id = id;
        }

        
        public void run() {

            for (int j = 1; j <= 6; j++) {

                int v = (id * 37 + j * 11) % 97 + 1;

                try {

                    mutex.acquire();

                    try {

                        checks++;

                        if (v > best) {

                            best = v;
                            bestBy = id;

                        }

                    } finally {

                        mutex.release();

                    }

                } catch (InterruptedException e) {

                    Thread.currentThread().interrupt();
                    return;

                }
            }
        }
    }

    public static void main(String[] args)
            throws InterruptedException {

        Teacher[] teachers = new Teacher[5];

      
        for (int i = 0; i < 5; i++) {

            teachers[i] = new Teacher(i + 1);

        }

     
        for (int i = 0; i < 5; i++) {

            teachers[i].start();

        }

       
        for (int i = 0; i < 5; i++) {

            teachers[i].join();

        }

        
        System.out.println("Best score: " + best);

        System.out.println(
            "Found by Teacher number: " + bestBy
        );

        System.out.println("Checks made: " + checks);

    }
}