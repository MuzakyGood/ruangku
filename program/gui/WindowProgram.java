package program.gui;

import java.awt.Font;
import java.text.SimpleDateFormat;
import java.awt.Color;

import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;

import java.util.Date;
import java.util.Calendar;

import program.gui.util.FileLoader;
import program.gui.util.ButtonImage;
import program.gui.util.EventHandling;
import program.gui.util.RoundedBorderInput;

import program.process.Reservation;
import program.process.ReservationException;

public class WindowProgram extends JFrame
{
    private JPanel mainLayout = new JPanel();
    private ImageIcon appIcon = new ImageIcon(FileLoader.find("img/room_reservation.png", false));

    private JLabel mainTitleLabel = new JLabel("RuangKu");

    private JLabel penanggungJawabLabel = new JLabel("Penanggung Jawab");
    private JLabel jenisRuanganLabel = new JLabel("Jenis Ruangan");
    private JLabel deskripsiRuanganLabel = new JLabel("Deskripsi Ruangan");
    private JLabel bayarKembalianLabel = new JLabel("Bayar & Kembalian");
    private JLabel tanggalReservasiLabel = new JLabel("Tanggal Reservasi Label");
    private JLabel waktuAcaraLabel = new JLabel("Waktu Acara");
    private JLabel totalHargaLabel = new JLabel("Total Harga");
    private JLabel logReservasiLabel = new JLabel("Log Reservasi");

    private JTextField namaPenanggungJawabField = new JTextField();
    private JTextField bayarField = new JTextField();
    private JTextField totalBayarField = new JTextField();
    private JTextField kembalianField = new JTextField();

    private String[] selectJenisRuanganList = {"Pilih Ruangan", "VIP", "Teater", "Meeting", "Aula", "Halaman"};
    private JComboBox<String> selectJenisRuanganBox = new JComboBox<>(selectJenisRuanganList);

    private JTextArea deskripsiRuanganArea = new JTextArea();
    private JTextArea logReservasiArea = new JTextArea();

    private JScrollPane deskripsiRuanganScrollPane = new JScrollPane(
        deskripsiRuanganArea,
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
    );
    private JScrollPane logReservasiScrollPane = new JScrollPane(
        logReservasiArea,
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
    );

    private JSpinner selectTanggalReservasi = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH));
    private JSpinner.DateEditor formatTanggalReservasi = new JSpinner.DateEditor(selectTanggalReservasi, "dd/MM/yyyy");

    private JSpinner selectJamFromReservasi = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE));
    private JSpinner selectJamToReservasi = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE));

    private JSpinner.DateEditor formatJamFromReservasi = new JSpinner.DateEditor(selectJamFromReservasi, "HH:mm");
    private JSpinner.DateEditor formatJamToReservasi = new JSpinner.DateEditor(selectJamToReservasi, "HH:mm");

    private JButton pesanBtn = new JButton("<html><center>Pesan Sekarang</center></html>");
    private JButton bersihBtn = new JButton("<html><center>Bersih</center></html>");

    private static final int DEFAULT_FONT_SIZE_INPUT = 12;
    private static final String DEFAULT_FONT = "Segoe UI Semibold";
    private static final String DEFAULT_TITLE_FONT = "Verdana";
    private static final String DEFAULT_INPUT_FONT = "Segoe UI";
    private static final Color DEFAULT_FONT_COLOR = Color.BLACK;
    private static final Font DEFAULT_FONT_STANDARD = new Font(DEFAULT_FONT, Font.PLAIN, 15);
    private static final Font DEFAULT_FONT_TITLE_STANDARD = new Font(DEFAULT_TITLE_FONT, Font.BOLD, 25);
    private static final EmptyBorder DEFAULT_INPUT_PADDING_BOX = new EmptyBorder(3, 3, 3, 3);
    private static final RoundedBorderInput DEFAULT_INPUT_BORDER_BOX = new RoundedBorderInput(5, 2, Color.BLACK);

    // Flip-flop toggle member scaleBtnDescRoom
    private boolean isToggleScaleDescRoomBtn = false;
    private ButtonImage fullScaleDescRoomBtn = new ButtonImage(FileLoader.find("img/full_scale.png", false));

    // Flip-flop toggle member scaleBtnReservRoom
    private boolean isToggleScaleLogReservRoomBtn = false;
    private ButtonImage fullScaleReservRoomBtn = new ButtonImage(FileLoader.find("img/full_scale.png", false));

    private Reservation reservProcess = new Reservation();
    private EventHandling hEvent = new EventHandling();

    private void fullAreaRoomDescription()
    {
        // Do flip-flop toggle
        isToggleScaleDescRoomBtn = !isToggleScaleDescRoomBtn;

        if (isToggleScaleDescRoomBtn)
        {
            totalBayarField.setVisible(false);
            logReservasiArea.setVisible(false);
            fullScaleReservRoomBtn.getButton().setVisible(false);
            deskripsiRuanganArea.setBounds(20, 25, 620, 450);
            fullScaleDescRoomBtn.setBounds(585, 40, 35, 35);

            if (selectJenisRuanganBox.getSelectedIndex() != 0)
                deskripsiRuanganLabel.setText(String.format("Deskripsi Ruangan %s", selectJenisRuanganBox.getSelectedObjects()));
            else deskripsiRuanganLabel.setText("Informasi");

            deskripsiRuanganLabel.setBounds(20, 0, 250, 25);
            deskripsiRuanganLabel.setFont(new Font(DEFAULT_TITLE_FONT, Font.BOLD, 15));
        }
        else
        {
            totalBayarField.setVisible(true);
            logReservasiArea.setVisible(true);
            fullScaleReservRoomBtn.getButton().setVisible(true);
            deskripsiRuanganArea.setBounds(200, 140, 110, 65);
            fullScaleDescRoomBtn.setBounds(313, 140, 20, 20);
            deskripsiRuanganLabel.setText("Deskripsi Ruangan");
            deskripsiRuanganLabel.setBounds(20,153,200,25);
            deskripsiRuanganLabel.setFont(DEFAULT_FONT_STANDARD);
        }
    }

    private void fullAreaLogReservRoom()
    {
        // Do flip-flop toggle
        isToggleScaleLogReservRoomBtn = !isToggleScaleLogReservRoomBtn;

        if (isToggleScaleLogReservRoomBtn)
        {
            totalBayarField.setVisible(false);
            deskripsiRuanganArea.setVisible(false);
            fullScaleDescRoomBtn.getButton().setVisible(false);
            logReservasiArea.setBounds(20, 25, 620, 450);
            fullScaleReservRoomBtn.setBounds(585, 40, 35, 35);
            logReservasiLabel.setBounds(20, 0, 200, 25);
        }
        else
        {
            totalBayarField.setVisible(true);
            deskripsiRuanganArea.setVisible(true);
            fullScaleDescRoomBtn.getButton().setVisible(true);
            logReservasiArea.setBounds(20, 385, 620, 100);
            fullScaleReservRoomBtn.setBounds(645, 385, 20, 20);
            logReservasiLabel.setBounds(20, 355, 200, 25);
        }
    }

    private void showDescriptionRoom()
    {
        if (selectJenisRuanganBox.getSelectedItem().equals("Pilih Ruangan"))
            deskripsiRuanganArea.setText(
            "Pilih ruangan dulu."
            );
        else if (selectJenisRuanganBox.getSelectedItem().equals("VIP"))
            deskripsiRuanganArea.setText(
            "Ruangan tertutup eksklusif berinterior premium dengan fasilitas khusus, memiliki kapasitas intim (sekitar 5-15 orang) yang menawarkan suasana tenang, berkelas, dan sangat menjaga privasi."
            );
        else if (selectJenisRuanganBox.getSelectedItem().equals("Teater"))
            deskripsiRuanganArea.setText(
            "Ruangan berlantai undak (tiered seating) dengan akustik dan pencahayaan khusus yang menghadap satu titik fokus panggung atau layar, berkapasitas menengah hingga besar dengan suasana yang fokus, terpusat, serta formal atau dramatis."
            );
        else if (selectJenisRuanganBox.getSelectedItem().equals("Meeting"))
            deskripsiRuanganArea.setText(
            "Ruangan fungsional yang berpusat pada sebuah meja besar beserta fasilitas presentasi lengkap, berkapasitas kecil hingga menengah (10-30 orang) untuk menciptakan suasana kerja yang kolaboratif, interaktif, dan profesional."
            );
        else if (selectJenisRuanganBox.getSelectedItem().equals("Aula"))
            deskripsiRuanganArea.setText(
            "Ruangan indoor datar dan sangat luas tanpa sekat pilar yang tata letaknya sangat fleksibel, berkapasitas skala besar (ratusan hingga ribuan orang) dengan nuansa yang megah, dinamis, dan ramai."
            );
        else if (selectJenisRuanganBox.getSelectedItem().equals("Halaman"))
            deskripsiRuanganArea.setText(
            "Area terbuka (outdoor atau semi-outdoor) menyatu dengan alam bersirkulasi udara bebas, memiliki kapasitas yang sangat fleksibel (tergantung luas area) dengan suasana yang kasual, santai, segar, dan interaktif."
            );
    }

    private void clearForm()
    {
        namaPenanggungJawabField.setText("");
        totalBayarField.setText("");
        bayarField.setText("");
        kembalianField.setText("");
        selectJenisRuanganBox.setSelectedIndex(0);

        // Set default real time clock
        Date now = new Date();

        selectTanggalReservasi.setValue(now);
        selectJamFromReservasi.setValue(now);
        selectJamToReservasi.setValue(now);
    }

    private void viewPayReservation()
    {
        var reservDate = (Date)selectTanggalReservasi.getValue();
        var timeFrom = (Date)selectJamFromReservasi.getValue();
        var timeTo = (Date)selectJamToReservasi.getValue();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        var roomIndex = selectJenisRuanganBox.getSelectedIndex();
        var reservDateStr = dateFormat.format(reservDate);
        var reservTimeFromStr = timeFormat.format(timeFrom);
        var reservTimeToStr = timeFormat.format(timeTo);

        try
        {
            reservProcess.getViewPay(
                roomIndex,
                reservDateStr, reservTimeFromStr, 
                reservTimeToStr);

            String byrStr = String.valueOf(reservProcess.getTotalBayar());
            String totalBayarStr = "Rp. " + byrStr;

            totalBayarField.setText(totalBayarStr);
        }
        catch (ReservationException e)
        {
            totalBayarField.setText("Rp. 0.0");
        }
    }

    private void processPay()
    {
        var nama = "";
        var bayarReservasi = 0.0f;
        var reservDate = (Date)selectTanggalReservasi.getValue();
        var timeFrom = (Date)selectJamFromReservasi.getValue();
        var timeTo = (Date)selectJamToReservasi.getValue();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        nama = namaPenanggungJawabField.getText();
        if (nama.equals(""))
        {
            JOptionPane.showMessageDialog(
                null, 
                "Input nama penanggung jawab tidak boleh kosong!",
                "Error Input",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        var roomIndex = selectJenisRuanganBox.getSelectedIndex();
        var reservDateStr = dateFormat.format(reservDate);
        var reservTimeFromStr = timeFormat.format(timeFrom);
        var reservTimeToStr = timeFormat.format(timeTo);

        try
        {
            bayarReservasi = Float.parseFloat(bayarField.getText());
        }
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(
                null, 
                "Input yang di masukan harus berupa angka!",
                "Error Input",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try
        {
            reservProcess.setProcessPay(
                nama, roomIndex, reservDateStr,
                reservTimeFromStr, reservTimeToStr,
                bayarReservasi
            );

            String byrStr = String.valueOf(reservProcess.getTotalBayar());
            String kmbliStr = String.valueOf(reservProcess.getUangKembali());
            String totalBayarStr = "Rp. " + byrStr;
            String totalUangKembaliStr = "Rp. " + kmbliStr;

            totalBayarField.setText(totalBayarStr);
            kembalianField.setText(totalUangKembaliStr);
        }
        catch (ReservationException e)
        {
            JOptionPane.showMessageDialog(
                null, 
                e.getMessage(),
                "Error Reservation Room",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    public WindowProgram(String title, int width, int heigh, boolean canResizeWindow)
    {
        mainLayout.setLayout(null);
        mainLayout.setBackground(new Color(181, 198, 211));

        // === LAYER COMPONENT ===

        mainLayout.add(fullScaleDescRoomBtn.getButton());
        mainLayout.add(fullScaleReservRoomBtn.getButton());
        mainLayout.add(deskripsiRuanganArea);
        mainLayout.add(logReservasiArea);

        mainLayout.add(mainTitleLabel);
        mainLayout.add(penanggungJawabLabel);
        mainLayout.add(jenisRuanganLabel);
        mainLayout.add(deskripsiRuanganLabel);
        mainLayout.add(bayarKembalianLabel);
        mainLayout.add(tanggalReservasiLabel);
        mainLayout.add(waktuAcaraLabel);
        mainLayout.add(totalHargaLabel);
        mainLayout.add(logReservasiLabel);

        mainLayout.add(namaPenanggungJawabField);
        mainLayout.add(bayarField);
        mainLayout.add(totalBayarField);
        mainLayout.add(kembalianField);

        mainLayout.add(selectJenisRuanganBox);

        mainLayout.add(deskripsiRuanganScrollPane);
        mainLayout.add(logReservasiScrollPane);

        mainLayout.add(selectTanggalReservasi);
        mainLayout.add(selectJamFromReservasi);
        mainLayout.add(selectJamToReservasi);

        mainLayout.add(pesanBtn);
        mainLayout.add(bersihBtn);

        add(mainLayout);

        // === COMPONENT POSITION ===

        mainTitleLabel.setForeground(DEFAULT_FONT_COLOR);
        mainTitleLabel.setFont(DEFAULT_FONT_TITLE_STANDARD);
        mainTitleLabel.setBounds(285, 20, 200, 35);

        penanggungJawabLabel.setFont(DEFAULT_FONT_STANDARD);
        penanggungJawabLabel.setForeground(DEFAULT_FONT_COLOR);
        penanggungJawabLabel.setBounds(20,78,200,25);

        jenisRuanganLabel.setFont(DEFAULT_FONT_STANDARD);
        jenisRuanganLabel.setForeground(DEFAULT_FONT_COLOR);
        jenisRuanganLabel.setBounds(20,110,200,25);    
        
        deskripsiRuanganLabel.setFont(DEFAULT_FONT_STANDARD);
        deskripsiRuanganLabel.setForeground(DEFAULT_FONT_COLOR);
        deskripsiRuanganLabel.setBounds(20,153,200,25); 
        
        bayarKembalianLabel.setFont(DEFAULT_FONT_STANDARD);
        bayarKembalianLabel.setForeground(DEFAULT_FONT_COLOR);
        bayarKembalianLabel.setBounds(20,233,200,25);

        tanggalReservasiLabel.setFont(DEFAULT_FONT_STANDARD);
        tanggalReservasiLabel.setForeground(DEFAULT_FONT_COLOR);
        tanggalReservasiLabel.setBounds(325, 75, 200, 35);

        waktuAcaraLabel.setFont(DEFAULT_FONT_STANDARD);
        waktuAcaraLabel.setForeground(DEFAULT_FONT_COLOR);
        waktuAcaraLabel.setBounds(325, 153, 200, 25);

        totalHargaLabel.setFont(DEFAULT_FONT_STANDARD);
        totalHargaLabel.setForeground(DEFAULT_FONT_COLOR);
        totalHargaLabel.setBounds(325, 213, 200, 25);

        logReservasiLabel.setForeground(DEFAULT_FONT_COLOR);
        logReservasiLabel.setBounds(20, 355, 200, 25);
        logReservasiLabel.setFont(new Font(DEFAULT_TITLE_FONT, Font.BOLD, 15));

        namaPenanggungJawabField.setBounds(200, 80, 110, 25);
        namaPenanggungJawabField.setFont(new Font(DEFAULT_INPUT_FONT, Font.PLAIN, DEFAULT_FONT_SIZE_INPUT));
        namaPenanggungJawabField.setBorder(BorderFactory.createCompoundBorder(
            DEFAULT_INPUT_BORDER_BOX,
            DEFAULT_INPUT_PADDING_BOX
        ));

        bayarField.setBounds(200, 215, 110, 25);
        bayarField.setFont(new Font(DEFAULT_INPUT_FONT, Font.PLAIN, DEFAULT_FONT_SIZE_INPUT));
        bayarField.setBorder(BorderFactory.createCompoundBorder(
            DEFAULT_INPUT_BORDER_BOX,
            DEFAULT_INPUT_PADDING_BOX
        ));

        totalBayarField.setEditable(false);
        totalBayarField.setBounds(500, 215, 110, 25);
        totalBayarField.setFont(new Font(DEFAULT_INPUT_FONT, Font.PLAIN, DEFAULT_FONT_SIZE_INPUT));
        totalBayarField.setBorder(BorderFactory.createCompoundBorder(
            DEFAULT_INPUT_BORDER_BOX,
            DEFAULT_INPUT_PADDING_BOX
        ));

        kembalianField.setEditable(false);
        kembalianField.setBounds(200, 255, 110, 25);
        kembalianField.setFont(new Font(DEFAULT_INPUT_FONT, Font.PLAIN, DEFAULT_FONT_SIZE_INPUT));
        kembalianField.setBorder(BorderFactory.createCompoundBorder(
            DEFAULT_INPUT_BORDER_BOX,
            DEFAULT_INPUT_PADDING_BOX
        ));

        selectJenisRuanganBox.setBounds(200, 110, 110, 25);

        deskripsiRuanganArea.setEditable(false);
        deskripsiRuanganArea.setLineWrap(true);
        deskripsiRuanganArea.setWrapStyleWord(true);
        deskripsiRuanganArea.setText("Pilih ruangan dulu.");
        deskripsiRuanganArea.setBounds(200, 140, 110, 65);
        deskripsiRuanganArea.setFont(new Font(DEFAULT_INPUT_FONT, Font.PLAIN, DEFAULT_FONT_SIZE_INPUT));
        deskripsiRuanganArea.setBorder(BorderFactory.createCompoundBorder(
            DEFAULT_INPUT_BORDER_BOX,
            DEFAULT_INPUT_PADDING_BOX
        ));

        logReservasiArea.setEditable(false); 
        logReservasiArea.setLineWrap(false);
        logReservasiArea.setWrapStyleWord(false);
        logReservasiArea.setBounds(20, 385, 620, 100);
        logReservasiArea.setFont(new Font(DEFAULT_INPUT_FONT, Font.PLAIN, DEFAULT_FONT_SIZE_INPUT));
        logReservasiArea.setBorder(BorderFactory.createCompoundBorder(
            DEFAULT_INPUT_BORDER_BOX,
            DEFAULT_INPUT_PADDING_BOX
        ));

        fullScaleDescRoomBtn.setBounds(313, 140, 20, 20);
        fullScaleReservRoomBtn.setBounds(645, 385, 20, 20);

        selectTanggalReservasi.setVisible(true);
        selectTanggalReservasi.setEditor(formatTanggalReservasi);
        selectTanggalReservasi.setBounds(500, 75, 85, 35);

        selectJamFromReservasi.setVisible(true);
        selectJamFromReservasi.setEditor(formatJamFromReservasi);
        selectJamFromReservasi.setBounds(500, 125, 85, 33);

        selectJamToReservasi.setVisible(true);
        selectJamToReservasi.setEditor(formatJamToReservasi);
        selectJamToReservasi.setBounds(500, 175, 85, 33);

        pesanBtn.setBounds(250, 300, 85, 45);
        bersihBtn.setBounds(350, 300, 85, 45);

        // === EVENT HANDLER AREA ===

        hEvent.from(fullScaleDescRoomBtn.getButton(), () -> fullAreaRoomDescription());
        hEvent.from(fullScaleReservRoomBtn.getButton(), () -> fullAreaLogReservRoom());
        hEvent.from(bersihBtn, () -> clearForm());
        hEvent.from(pesanBtn, () -> processPay());

        // === EVENT MAIN LOOP (REAL TIME) ===

        Timer realTimeLoop = new Timer(500, e -> {
            showDescriptionRoom();
            viewPayReservation();
            logReservasiArea.setText(reservProcess.getAllRoomsStatus());
        });
        realTimeLoop.start();

        // === WINDOW APP SETUP ===
        
        setTitle(title);
        setSize(width, heigh);
        setResizable(canResizeWindow);
        setIconImage(appIcon.getImage());
        setVisible(true);
        setDefaultCloseOperation(WindowProgram.EXIT_ON_CLOSE);
    }
}