/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nguyenmanh.frame;

import com.nguyenmanh.dao.InvoiceDao;
import com.nguyenmanh.dao.ProductDao;
import com.nguyenmanh.dao.TableDao;
import com.nguyenmanh.model.Invoice;
import com.nguyenmanh.model.Product;
import com.nguyenmanh.model.Table;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 * @author Nguyen Manh
 */
public final class Main extends javax.swing.JFrame {
    
    private int nTable;
    private int nProduct;
    private ArrayList<Table> arrTable;
    private ArrayList<Product> arrProduct;
    private ArrayList<Invoice> arrInvoice;
    private ArrayList<JButton> arrBtnTable;
    private final String[] columnName = {"STT", "Mã SP", "Tên SP", "Đơn Vị Tính", "Số lượng", "Đơn giá", "Thành Tiền"};
    
   /**
     * Creates new form NguyenManh
     */
    public Main() {
        initComponents();
        setResizable(false);
        setLocation(100,5);
        setTitle("Quản lý dịch vụ");
        txtNgayThang.setText(getTime());
        loadData();
        viewTable();
        viewProduct();
    }
    
    /**
     * Function getTime for date
     */
    public String getTime(){
          Date date = new Date();
          SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
          return format.format(date);
    }
    
    /**
     * Function loadData
     */
    private void loadData(){
        TableDao tableDao = new TableDao();
        arrTable = tableDao.getAllTable();
        ProductDao productDao = new ProductDao();
        arrProduct = productDao.getAllProduct();
        arrInvoice = new ArrayList<>();
        for (int i = 0; i < arrTable.size(); i++) {
            Invoice invoice = new Invoice();
            invoice.setTable(arrTable.get(i));
            invoice.setArrProduct(productDao.getAllProduct());
            arrInvoice.add(invoice);
        }
    }
    
     /**
     * Function display Table Cafe
     */
    private void viewTable(){
        arrBtnTable = new ArrayList<>();
        panelDanhSachBan.setLayout(new GridLayout(arrTable.size() / 2, 2 + 1, 8, 8));
        for (final Table object : arrTable) {
            final JButton btnTable = new JButton(object.getTable_name());
            btnTable.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    txtSoBan.setText(object.getTable_name());
                    btnTable.setBackground(Color.RED);
                    object.setTable_status(true);
                    nTable = findNumBerTable(object);
                    viewCart(arrInvoice.get(nTable).getArrProduct());
                }
            });
            panelDanhSachBan.add(btnTable);
            arrBtnTable.add(btnTable);
        }
    }
     /**
     * Function display Product
     */
    private void viewProduct(){
        JLabel lbNameProduct = new JLabel("Đồ Uống", JLabel.LEFT);
        JLabel lbProductPrice = new JLabel("Giá Bán", JLabel.CENTER);
        JLabel lbChooseProduct = new JLabel("Lựa Chọn "); 
        JLabel lbChoosePrice = new JLabel("Đơn Vị", JLabel.RIGHT);
        lbChooseProduct.setSize(5,5);
        
        panelDanhSachSanPham.setLayout(new GridLayout(0,4));
        panelDanhSachSanPham.add(lbChooseProduct);
        panelDanhSachSanPham.add(lbNameProduct);
        panelDanhSachSanPham.add(lbProductPrice);
        panelDanhSachSanPham.add(lbChoosePrice);
        
        for(final Product object: arrProduct){
            JLabel btnChoose = new JLabel("", resizeImage("/Images/"+object.getProduct_image()),
                    JLabel.LEFT);
            btnChoose.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (txtSoBan.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Bạn chưa chọn bàn");
                    }
                    else{
                        nProduct = findNumberProduct(object);
                        int quantity = arrInvoice.get(nTable).getArrProduct().get(nProduct).getProduct_quantity_sell()+1;
                        arrInvoice.get(nTable).getArrProduct().get(nProduct).setProduct_quantity_sell(quantity);
                        viewCart(arrInvoice.get(nTable).getArrProduct());
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    
                }
                
                @Override
                public void mouseReleased(MouseEvent e) {
                    
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    
                }
            });
            panelDanhSachSanPham.add(btnChoose);
            panelDanhSachSanPham.add(new JLabel(object.getProduct_name() +"  ", JLabel.LEFT));
            panelDanhSachSanPham.add(new JLabel(object.getProduct_price() +" ", JLabel.CENTER));
            panelDanhSachSanPham.add(new JLabel(object.getProduct_unit()+" ", JLabel.RIGHT));
        }
    }
    
     /**
     * Function resizeImage
     */
    private Icon resizeImage(String url){
        Image image = (new ImageIcon(getClass().getResource(url))).getImage();
        BufferedImage resizeImage = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = resizeImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(image, 0, 0, 40, 40, null);
        graphics2D.dispose();
        
        return new ImageIcon(resizeImage);
    }

    
     /**
     * Function find table and numProduct
     */
    private int findNumBerTable(Table table){
        for (int i = 0; i < arrTable.size(); i++) {
            if (table.getTable_id() == arrTable.get(i).getTable_id()) {
                return i;
            }
        }
        return -1;
    }
    
    private int findNumberProduct(Product p){
        for (int i = 0; i < arrProduct.size(); i++) {
            if (p.getProduct_id() == arrProduct.get(i).getProduct_id()) {
                return i;
            }
        }
        return -1;
    }
   
     /**
     * Function view Cart
     */
    private void viewCart(ArrayList<Product> arrProduct){
        DefaultTableModel tableModel = new DefaultTableModel(columnName, arrProduct.size() +1);
        int k = 1;
        for (int i = 0; i < arrProduct.size(); i++) {
            Product p = arrProduct.get(i);
            if (p.getProduct_quantity_sell() > 0) {
                tableModel.setValueAt((k), k, 0);
                tableModel.setValueAt(p.getProduct_id(), k, 1);
                tableModel.setValueAt(p.getProduct_name(), k, 2);
                tableModel.setValueAt(p.getProduct_unit(), k, 3);
                tableModel.setValueAt(p.getProduct_quantity_sell(), k, 4);
                tableModel.setValueAt(p.getProduct_price(), k, 5);
                tableModel.setValueAt(p.total_product_price(), k, 6);
                k++;
            }
        }
        txtTongTien.setText(arrInvoice.get(nTable).total_money_price()+ "");
        hoaDon.setModel(tableModel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jtblSuDungDichVu = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        panelDanhSachBan = new javax.swing.JPanel();
        panelDanhSachSanPham = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        hoaDon = new javax.swing.JTable();
        THÊM2 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JTextField();
        btnThanhToan = new javax.swing.JButton();
        txtNgayThang = new javax.swing.JTextField();
        txtSoBan = new javax.swing.JTextField();
        btnHuy = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        btnListBan = new javax.swing.JButton();
        btnmenu = new javax.swing.JButton();
        btnHoaDon = new javax.swing.JButton();
        btnQuanTri = new javax.swing.JButton();
        desktop = new javax.swing.JDesktopPane();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        jButton1.setText("jButton1");

        jToggleButton1.setText("jToggleButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font(".VnArabia", 3, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 51, 0));
        jLabel1.setText("Trung Nguyen Coffe");

        jtblSuDungDichVu.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jtblSuDungDichVu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtblSuDungDichVuMouseClicked(evt);
            }
        });

        panelDanhSachBan.setBackground(new java.awt.Color(204, 204, 204));
        panelDanhSachBan.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh Sách Bàn", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

        javax.swing.GroupLayout panelDanhSachBanLayout = new javax.swing.GroupLayout(panelDanhSachBan);
        panelDanhSachBan.setLayout(panelDanhSachBanLayout);
        panelDanhSachBanLayout.setHorizontalGroup(
            panelDanhSachBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 257, Short.MAX_VALUE)
        );
        panelDanhSachBanLayout.setVerticalGroup(
            panelDanhSachBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        panelDanhSachSanPham.setBackground(new java.awt.Color(204, 204, 204));
        panelDanhSachSanPham.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh Sách Đồ Uống", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N
        panelDanhSachSanPham.setToolTipText("");

        javax.swing.GroupLayout panelDanhSachSanPhamLayout = new javax.swing.GroupLayout(panelDanhSachSanPham);
        panelDanhSachSanPham.setLayout(panelDanhSachSanPhamLayout);
        panelDanhSachSanPhamLayout.setHorizontalGroup(
            panelDanhSachSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 321, Short.MAX_VALUE)
        );
        panelDanhSachSanPhamLayout.setVerticalGroup(
            panelDanhSachSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(189, 188, 188));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa Đơn", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

        jLabel20.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jLabel21.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel21.setText("Ngày Tháng :");

        jLabel22.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel22.setText("Số Bàn :");

        hoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        hoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hoaDonMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(hoaDon);

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel23.setText("Tổng tiền :");

        txtTongTien.setEditable(false);
        txtTongTien.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtTongTien.setForeground(new java.awt.Color(255, 0, 0));
        txtTongTien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTongTienActionPerformed(evt);
            }
        });

        btnThanhToan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/save_all.png"))); // NOI18N
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        txtNgayThang.setEditable(false);
        txtNgayThang.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N

        txtSoBan.setEditable(false);
        txtSoBan.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N

        btnHuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/delete.png"))); // NOI18N
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(255, 102, 102));
        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 51, 51));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/dollar.png"))); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addGap(28, 28, 28)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSoBan, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNgayThang, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(THÊM2)
                .addGap(104, 104, 104))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)))
                .addContainerGap())
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(txtNgayThang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSoBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel23)))
                .addGap(18, 18, 18)
                .addComponent(THÊM2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panelDanhSachBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelDanhSachSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelDanhSachBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelDanhSachSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jtblSuDungDichVu.addTab("Sử Dụng Dịch Vụ", jPanel1);

        jPanel2.setPreferredSize(new java.awt.Dimension(907, 476));

        btnListBan.setForeground(new java.awt.Color(51, 0, 0));
        btnListBan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Hotel-36-64.png"))); // NOI18N
        btnListBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListBanActionPerformed(evt);
            }
        });

        btnmenu.setForeground(new java.awt.Color(51, 0, 0));
        btnmenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/menu.png"))); // NOI18N
        btnmenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmenuActionPerformed(evt);
            }
        });

        btnHoaDon.setForeground(new java.awt.Color(102, 0, 0));
        btnHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/finance-48-64.png"))); // NOI18N
        btnHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoaDonActionPerformed(evt);
            }
        });

        btnQuanTri.setForeground(new java.awt.Color(51, 0, 0));
        btnQuanTri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Admin_Roles-64.png"))); // NOI18N
        btnQuanTri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuanTriActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout desktopLayout = new javax.swing.GroupLayout(desktop);
        desktop.setLayout(desktopLayout);
        desktopLayout.setHorizontalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 980, Short.MAX_VALUE)
        );
        desktopLayout.setVerticalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnListBan, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnmenu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnHoaDon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE))
                    .addComponent(btnQuanTri, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(desktop)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(desktop)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnListBan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(btnmenu, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnQuanTri, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(55, 55, 55)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jtblSuDungDichVu.addTab("Quản Lý Dịch Vụ", jPanel2);

        jLabel4.setFont(new java.awt.Font("Tempus Sans ITC", 3, 18)); // NOI18N
        jLabel4.setText("Coffee No. 1 Vietnam");

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/title.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(336, 336, 336)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)))
                .addGap(255, 255, 255)
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jtblSuDungDichVu)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4))
                            .addComponent(jLabel6)))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtblSuDungDichVu))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //click vào menu
    private void btnmenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmenuActionPerformed


    }//GEN-LAST:event_btnmenuActionPerformed

    //click list bàn
    private void btnListBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListBanActionPerformed
        // TODO add your handling code here:
       

    }//GEN-LAST:event_btnListBanActionPerformed

    private void jtblSuDungDichVuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtblSuDungDichVuMouseClicked

    }//GEN-LAST:event_jtblSuDungDichVuMouseClicked

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        setValueOnClick("Đã hủy chọn bàn");
    }//GEN-LAST:event_btnHuyActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed

        InvoiceDao invoiceDao = new InvoiceDao();
        Invoice invoice = arrInvoice.get(nTable);
        if (invoice.total_product() > 0) {
            invoiceDao.payInvoice(arrInvoice.get(nTable));
            setValueOnClick("Thanh toán thành công!");
        }
        else{
            JOptionPane.showMessageDialog(null, "Chưa có sản phẩm được chọn");
        }
    }//GEN-LAST:event_btnThanhToanActionPerformed
    
    private void setValueOnClick(String s){
        txtSoBan.setText(null);
        txtTongTien.setText(null);
        hoaDon.setModel(new DefaultTableModel(columnName, 0));
        for (int i = 0; i < arrProduct.size(); i++) {
            arrInvoice.get(nTable).getArrProduct().get(i).setProduct_quantity_sell(0);
        }
        arrTable.get(nTable).setTable_status(false);
        arrBtnTable.get(nTable).setBackground(Color.LIGHT_GRAY);
        JOptionPane.showMessageDialog(null, s);
    }

    
    private void txtTongTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTongTienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTongTienActionPerformed

    private void hoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hoaDonMouseClicked

    }//GEN-LAST:event_hoaDonMouseClicked

    //click vào quản trị
    private void btnQuanTriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuanTriActionPerformed
        Login login = new Login();
        this.setVisible(false);
        login.setVisible(true);
    }//GEN-LAST:event_btnQuanTriActionPerformed

    //click vào hóa đơn
    private void btnHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoaDonActionPerformed

    }//GEN-LAST:event_btnHoaDonActionPerformed


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel THÊM2;
    private javax.swing.JButton btnHoaDon;
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnListBan;
    private javax.swing.JButton btnQuanTri;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnmenu;
    private javax.swing.JDesktopPane desktop;
    private javax.swing.JTable hoaDon;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JTabbedPane jtblSuDungDichVu;
    private javax.swing.JPanel panelDanhSachBan;
    private javax.swing.JPanel panelDanhSachSanPham;
    private javax.swing.JTextField txtNgayThang;
    private javax.swing.JTextField txtSoBan;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables
}
