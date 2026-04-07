import java.util.*;
import java.text.DecimalFormat;

// A class to store runtime data for each process
class ProcessData {
    int runNumber;
    long runtime;
    double cpuLoad;

    ProcessData(int runNumber, long runtime, double cpuLoad) {
        this.runNumber = runNumber;
        this.runtime = runtime;
        this.cpuLoad = cpuLoad;
    }
}
// Class that performs runtime analysis
class RuntimeAnalyzer {
    private List<ProcessData> records = new ArrayList<>();
    private Random rand = new Random();

    // Simulate execution of a process
    public void runProcess(int runNumber) {
        System.out.println("\n--- Run #" + runNumber + " ---");
        long start = System.currentTimeMillis();

        // Simulate CPU load (0.0 - 1.0)
        double cpuLoad = rand.nextDouble();

        // Simulate variable task runtime (depends slightly on CPU load)
        int simulatedWork = (int) ((cpuLoad * 300) + 200); // 200–500 ms
        try {
            Thread.sleep(simulatedWork);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        long runtime = end - start;

        records.add(new ProcessData(runNumber, runtime, cpuLoad));
        System.out.printf("CPU Load: %.2f | Runtime: %d ms%n", cpuLoad, runtime);
    }

    // Analyze performance
    public void analyzePerformance() {
        if (records.isEmpty()) {
            System.out.println("No data recorded yet.");
            return;
        }

        double avgRuntime = getAverageRuntime();
        double stdDev = getStandardDeviation(avgRuntime);
        double avgCpu = getAverageCpuLoad();

        DecimalFormat df = new DecimalFormat("#.##");
System.out.println("\n========= Analysis Report =========");
        System.out.println("Total Runs: " + records.size());
        System.out.println("Average Runtime: " + df.format(avgRuntime) + " ms");
        System.out.println("CPU Load Average: " + df.format(avgCpu));
        System.out.println("Runtime Standard Deviation: " + df.format(stdDev));
        System.out.println("===================================");
    }

    // Predict next run performance (simple AI rule-based prediction)
    public void predictNextRun() {
        if (records.size() < 2) {
            System.out.println("Not enough data to predict yet.");
            return;
        }

        ProcessData last = records.get(records.size() - 1);
        ProcessData prev = records.get(records.size() - 2);

        String prediction;

        if (last.runtime > prev.runtime + 50 && last.cpuLoad > prev.cpuLoad) {
            prediction = "Prediction: Next run might be SLOWER (CPU load increasing).";
        } else if (last.runtime < prev.runtime - 50 && last.cpuLoad < prev.cpuLoad) {
            prediction = "Prediction: Next run might be FASTER (CPU load decreasing).";
        } else {
            prediction = "Prediction: Runtime likely to remain STABLE.";
        }

        System.out.println(prediction);
    }

    // Utility methods
    private double getAverageRuntime() {
        long total = 0;
        for (ProcessData p : records)
            total += p.runtime;
        return (double) total / records.size();
    }

    private double getStandardDeviation(double mean) {
        double sum = 0;
        for (ProcessData p : records)
            sum += Math.pow(p.runtime - mean, 2);
        return Math.sqrt(sum / records.size());
    }

    private double getAverageCpuLoad() {
        double total = 0;
        for (ProcessData p : records)
            total += p.cpuLoad;
        return total / records.size();
    }
}

public class RuntimeBehaviorAnalyzer {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RuntimeAnalyzer analyzer = new RuntimeAnalyzer();

        System.out.println("=== RUNTIME BEHAVIOUR ANALYSER ===");
        System.out.print("Enter number of runs to simulate: ");
        int runs = sc.nextInt();

        for (int i = 1; i <= runs; i++) {
            analyzer.runProcess(i);
            analyzer.analyzePerformance();
            analyzer.predictNextRun();
        }

        System.out.println("\nFinal Summary:");
        analyzer.analyzePerformance();
        System.out.println("=== Analysis Complete ===");
        sc.close();
    }
}

