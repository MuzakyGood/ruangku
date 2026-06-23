package program.process;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Reservation
{
    private int jamReservasi;
    private float totalBayar, uangKembali;

    private String[] availableRooms = {
        "VIP",
        "Teater",
        "Meeting",
        "Aula",
        "Halaman"
    };
    private static final float[] hargaSatuJamRuangan = {
        200000.0f,
        500000.0f,
        100000.0f,
        1500000.0f,
        100000.0f
    };
    private String[] reservationStatus = {
        "Open Reservation.",
        "Open Reservation.",
        "Open Reservation.",
        "Open Reservation.",
        "Open Reservation."
    };
    private String[][] reservationDateTimeList = {
        {"", "", ""},
        {"", "", ""},
        {"", "", ""},
        {"", "", ""},
        {"", "", ""}
    };

    public Reservation(){}

    public float getTotalBayar() { return totalBayar; }
    public float getUangKembali() { return uangKembali; }

    public void setProcessPay(
        String name, int roomIndex,
        String reservDate, String timeReservFrom,
        String timeReservTo, float uangBayar) throws program.process.ReservationException
    {
        try
        {
            if (reservationStatus[roomIndex - 1].equals("Open Reservation."))
            {
                reservationDateTimeList[roomIndex - 1][0] = reservDate;
                reservationDateTimeList[roomIndex - 1][1] = timeReservFrom;
                reservationDateTimeList[roomIndex - 1][2] = timeReservTo;

                LocalTime start = LocalTime.parse(timeReservFrom);
                LocalTime end = LocalTime.parse(timeReservTo);
                Duration totalDuration = Duration.between(start, end);

                jamReservasi = (int)totalDuration.toHours();

                totalBayar = hargaSatuJamRuangan[roomIndex - 1] * jamReservasi;
                if (totalBayar == 0.0f)
                    throw new ReservationException("Jam reservasi minimal 1 jam!");

                uangKembali = uangBayar - totalBayar;

                String totalClock = String.format("%d hours %d minutes",
                    totalDuration.toHours(),
                    totalDuration.toMinutes() % 60
                );

                reservationStatus[roomIndex - 1] = String.format(
                    "Under Reservation by %s in %s at o'clock %s - %s (%s).",
                    name, 
                    reservDate,
                    timeReservFrom,
                    timeReservTo,
                    totalClock
                );
            }
            else throw new ReservationException("Ruangan sudah di reservasi!");
        }
        catch (ArrayIndexOutOfBoundsException e)
        { 
            throw new ReservationException("Ruangan reservasi tidak valid!");
        }
    }

    public void getViewPay(
        int roomIndex,
        String reservDate, String timeReservFrom,
        String timeReservTo) throws program.process.ReservationException
    {
        try
        {
            LocalTime start = LocalTime.parse(timeReservFrom);
            LocalTime end = LocalTime.parse(timeReservTo);
            Duration totalDuration = Duration.between(start, end);

            jamReservasi = (int)totalDuration.toHours();

            totalBayar = hargaSatuJamRuangan[roomIndex - 1] * jamReservasi;
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            throw new ReservationException("Ruangan reservasi tidak valid!");
        }
    }

    public String getAllRoomsStatus()
    {
        String temp = "";
        LocalDate tanggalSekarang = LocalDate.now();
        LocalTime jamSekarang = LocalTime.now();

        for (var i = 0; i < availableRooms.length; ++i)
        {   
            if (!reservationDateTimeList[i][0].equals(""))
            {
                LocalDate tglSelesai = LocalDate.parse(reservationDateTimeList[i][0], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalTime jamSelesai = LocalTime.parse(reservationDateTimeList[i][2], DateTimeFormatter.ofPattern("HH:mm"));
                
                if (tanggalSekarang.isAfter(tglSelesai) || 
                    (tanggalSekarang.isEqual(tglSelesai) &&
                    !jamSekarang.isBefore(jamSelesai)))
                {
                    reservationStatus[i] = "Open Reservation.";
                    reservationDateTimeList[i][0] = "";
                    reservationDateTimeList[i][1] = "";
                    reservationDateTimeList[i][2] = "";
                }
            }

            temp += String.format(
                "[%d] %s - %s\n",
                i+1,
                availableRooms[i],
                reservationStatus[i]
            );
        }

        return temp;
    }
}