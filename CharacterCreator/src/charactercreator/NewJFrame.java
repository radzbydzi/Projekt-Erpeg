/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charactercreator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import java.util.LinkedList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListModel;

/**
 *
 * @author USER
 */
public class NewJFrame extends javax.swing.JFrame {

    Color col1=new Color(0,0,0);
    Color col2=new Color(255,255,255);
    int actual_px_x=0;
    int actual_px_y=0;
    String tool="pencil";
    public class Sheet extends JPanel {
        int x=20;
        int y=20;
        int w=0;
        int h=0;
        int sheet_width=100;
        int sheet_height=300;
        int px_size=10;
        int px_in_world=1; // jeden pixel tutaj == [int] pixel(i) zwyczajny(ch)
        boolean grid=true;
        int percent=0;
        int current_animation=0;
        int current_frame=0;
        boolean cloth_making=false;//jak true to pracujemy na ubiorach
        int current_cloth=0;
        boolean show_model=true;//przy projektowaniu strojow czy widoczny ma byc model postaci
        List<Animation> animations=new LinkedList();
        class Clothing
        {
            public String name;
            List<Color[][]> cloth = new LinkedList();
            int w;
            int h;
            Clothing(int w, int h,String name)
            {
                this.w=w;
                this.h=h;
                this.name=name;
                cloth.add(new Color[w][h]);//dodanie pierwszej klatki
            }
        }
        class Animation
        {
            public String name;
            int w;
            int h;
            List<Color[][]> model = new LinkedList();//'nagi' model
            List<Clothing> clothing = new LinkedList();
            Animation(int w, int h,String name)
            {
                this.w=w;
                this.h=h;
                this.name=name;
                model.add(new Color[w][h]);//dodanie pierwszej klatki
            }
        }
        void addAnimation(String name)
        {
            //do listy
            animations.add(new Animation(w,h,name));
            //do list okienkowych
            DefaultListModel a_l_m = (DefaultListModel) anim_list.getModel();
            a_l_m.addElement(name);
        }
        void selectAnimation(String name)
        {
            for(int i=0; i<animations.size(); i++)
            {
                if(animations.get(i).name.equals(name))
                {
                    current_animation=i;
                    break;
                }
            }
            sheet.repaint();
        }
        void addClothing(String name)
        {
            if(animations.size()>0)
            {
               if(animations.get(current_animation)!=null)
               {
                   //do listy
                    animations.get(current_animation).clothing.add(new Clothing(w,h,name));
                    //do list okienkowych
                    DefaultListModel c_l_m = (DefaultListModel) cloth_list.getModel();
                    c_l_m.addElement(name);
               }
            }
            
            
        }
        void selectClothing(String name)
        {
            for(int i=0; i<animations.get(current_animation).clothing.size(); i++)
            {
                if(animations.get(current_animation).clothing.get(i).name.equals(name))
                {
                    current_cloth=i;
                    break;
                }
            }
            sheet.repaint();
        }
        void selectFrame(int frame)
        {
            current_animation=frame;
            sheet.repaint();
        }
        
        public Sheet(int w, int h) {
		//setPreferredSize(new Dimension(w, h));                
                this.w=w;
                this.h=h;
                
	}
        public void addPixel(int x, int y, Color color)
        {
            if(animations.size()>0 && current_animation<animations.size())
            {
                if(cloth_making==false)
                {
                    animations.get(current_animation).model.get(current_frame)[x][y]= color;
                }else
                {
                    animations.get(current_animation).clothing.get(current_cloth).cloth.get(current_frame)[x][y]=color;
                }
            }
            repaint();
        }
        public void setX(int x)
        {
            this.x=x;
        }
        public void setY(int y)
        {
            this.y=y;
        }
        public Dimension getXY()
        {
            return new Dimension(x,y);
        }
        public int[] getWandH()
        {
            int wh[]= {w,h};
            return wh;
        }
        @Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
                
                if(sheet.animations.size()>0 && current_animation<animations.size())
                {
                    Color [][] bittable;
                    
                    System.out.println("C M "+sheet.cloth_making);
                    if(cloth_making==false)
                    {
                        bittable = animations.get(current_animation).model.get(current_frame).clone();
                        System.out.println("-----------------------------------");
                        for(int i=0; i<bittable.length; i++)
                        {
                            for(int j=0; j<bittable[i].length;j++)
                            {
                                System.out.print(bittable[i][j]+" ");
                            }
                            System.out.println("");
                        }
                        System.out.println("-----------------------------------");
                        
                    }else
                    {
                        if(show_model==true)
                        {
                            bittable = animations.get(current_animation).model.get(current_frame).clone();
                            Color[][] tmp_col=animations.get(current_animation).clothing.get(current_cloth).cloth.get(current_frame).clone();
                            for(int i=0; i<bittable.length;i++)
                            {
                                for(int j=0; j<bittable[i].length;j++)
                                {                                    
                                    if(tmp_col[i][j]!=bittable[i][j] && tmp_col[i][j]!=null)
                                    {
                                        /*if(tmp_col[i][j].getAlpha()>0)//TODO: Przezroczyscosc
                                        {
                                            int R1,R2,G1,G2,B1,B2,A1,A2;
                                            R1=bittable[i][j].getRed();
                                            G1=bittable[i][j].getGreen();
                                            B1=bittable[i][j].getBlue();
                                            A1=bittable[i][j].getAlpha();
                                            
                                            R2=tmp_col[i][j].getRed();
                                            G2=tmp_col[i][j].getGreen();
                                            B2=tmp_col[i][j].getBlue();
                                            A2=bittable[i][j].getAlpha();
                                            
                                            bittable[i][j]+tmp_col[i][j];
                                        }
                                        else
                                        {
                                            bittable[i][j]=tmp_col[i][j];
                                        } */
                                        bittable[i][j]=tmp_col[i][j];
                                    }
                                }                                
                            }
                        }
                        else
                        {
                            bittable=animations.get(current_animation).clothing.get(current_cloth).cloth.get(current_frame).clone();
                        }
                        
                        
                    }
                    
                    for(int i=0; i<bittable.length; i++)
                    {
                        for(int j=0; j<bittable[i].length; j++)
                        {
                            if(bittable[i][j]!=null)
                            {
                                g2d.setColor(bittable[i][j]);
                            }
                            else
                            {
                                g2d.setColor(new Color(255,255,255));
                            }

                            g2d.fillRect(x+i*(px_size+percent), y+j*(px_size+percent), px_size+percent, px_size+percent);
                        }
                    }
                    g2d.setColor(new Color(0,0,0));

                    if(grid == true)
                    {
                        for(int i=0; i<bittable.length; i++)
                            for(int j=0; j<bittable[i].length; j++)
                                g2d.drawRect(x+i*(px_size+percent), y+j*(px_size+percent), px_size+percent, px_size+percent);
                    }
                }
                
	}
    }
    Sheet sheet = new Sheet(40,40);
    private void initializeWorkingArea()
    {
        workingarea.setBackground(new Color(100,100,100));//tworzenie tla
        
        sheet.addPixel(20, 30, new Color(255,0,0));
        requestFocus();
    }
    

    /**
     * Creates new form NewJFrame
     */
    public NewJFrame() {
        initComponents();
        color1.setBackground(col1);
        color2.setBackground(col2);
        initializeWorkingArea();
        //drawcircle(10,10,4);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sheetoptionframe = new javax.swing.JFrame();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        timeline = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        animation_timeline = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        toolbox = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        color1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        color2 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        layers = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        anim_list = new javax.swing.JList<>();
        jButton8 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        cloth_list = new javax.swing.JList<>();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        workingarea = sheet;
        jPanel2 = new javax.swing.JPanel();
        lupa_val = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        x_param = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        y_param = new javax.swing.JLabel();
        mod_ub = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();

        sheetoptionframe.setMinimumSize(new java.awt.Dimension(338, 240));
        sheetoptionframe.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                sheetoptionframeWindowActivated(evt);
            }
        });

        jLabel4.setText("Wielkość piksela");

        jLabel6.setText("Piksel projektu w normalnych pikselach");

        jLabel7.setText("Wyświetl siatkę");

        jButton12.setText("Anuluj");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setText("Zatwierdź");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField2)
                    .addComponent(jTextField1)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox2)))
                .addContainerGap(144, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton12)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jCheckBox2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton12)
                    .addComponent(jButton13))
                .addContainerGap())
        );

        javax.swing.GroupLayout sheetoptionframeLayout = new javax.swing.GroupLayout(sheetoptionframe.getContentPane());
        sheetoptionframe.getContentPane().setLayout(sheetoptionframeLayout);
        sheetoptionframeLayout.setHorizontalGroup(
            sheetoptionframeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        sheetoptionframeLayout.setVerticalGroup(
            sheetoptionframeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                formKeyTyped(evt);
            }
        });

        timeline.setBackground(new java.awt.Color(102, 102, 255));

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList1.setLayoutOrientation(javax.swing.JList.HORIZONTAL_WRAP);
        jList1.setVisibleRowCount(1);
        jScrollPane2.setViewportView(jList1);

        javax.swing.GroupLayout animation_timelineLayout = new javax.swing.GroupLayout(animation_timeline);
        animation_timeline.setLayout(animation_timelineLayout);
        animation_timelineLayout.setHorizontalGroup(
            animation_timelineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 713, Short.MAX_VALUE)
        );
        animation_timelineLayout.setVerticalGroup(
            animation_timelineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
        );

        jScrollPane3.setViewportView(animation_timeline);

        jButton14.setText("Dodaj");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jButton15.setText("Usuń");

        jButton16.setText("<");

        jButton17.setText(">");

        jCheckBox1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCheckBox1.setText("Pokaż poprzednią");

        javax.swing.GroupLayout timelineLayout = new javax.swing.GroupLayout(timeline);
        timeline.setLayout(timelineLayout);
        timelineLayout.setHorizontalGroup(
            timelineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, timelineLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(timelineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(timelineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, timelineLayout.createSequentialGroup()
                            .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jButton14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        timelineLayout.setVerticalGroup(
            timelineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timelineLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(timelineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(timelineLayout.createSequentialGroup()
                        .addComponent(jButton14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(timelineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton16)
                            .addComponent(jButton17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );

        toolbox.setBackground(new java.awt.Color(204, 204, 0));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/olowek.png"))); // NOI18N
        jButton1.setToolTipText("");
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/linia.png"))); // NOI18N
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/gumka.png"))); // NOI18N
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/wiadro.png"))); // NOI18N
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/zaznaczanie.png"))); // NOI18N
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/przenies.png"))); // NOI18N

        color1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                color1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout color1Layout = new javax.swing.GroupLayout(color1);
        color1.setLayout(color1Layout);
        color1Layout.setHorizontalGroup(
            color1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        color1Layout.setVerticalGroup(
            color1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 33, Short.MAX_VALUE)
        );

        jLabel1.setText("Kolor 1");

        jLabel2.setText("Kolor 2");

        color2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                color2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout color2Layout = new javax.swing.GroupLayout(color2);
        color2.setLayout(color2Layout);
        color2Layout.setHorizontalGroup(
            color2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        color2Layout.setVerticalGroup(
            color2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 33, Short.MAX_VALUE)
        );

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/zmianakol.png"))); // NOI18N
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout toolboxLayout = new javax.swing.GroupLayout(toolbox);
        toolbox.setLayout(toolboxLayout);
        toolboxLayout.setHorizontalGroup(
            toolboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toolboxLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(toolboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(color1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(color2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        toolboxLayout.setVerticalGroup(
            toolboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toolboxLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(color1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(color2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(145, Short.MAX_VALUE))
        );

        layers.setBackground(new java.awt.Color(51, 255, 102));

        anim_list.setModel(new DefaultListModel ());
        anim_list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        anim_list.setToolTipText("");
        anim_list.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                anim_listMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(anim_list);

        jButton8.setText("Dodaj");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton11.setText("Usuń");

        jButton18.setText("Duplikuj");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jButton18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton8)
                    .addComponent(jButton11)))
        );

        jTabbedPane1.addTab("Animacje", jPanel3);

        cloth_list.setModel(new DefaultListModel ());
        cloth_list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        cloth_list.setToolTipText("");
        cloth_list.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cloth_listMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(cloth_list);

        jButton19.setText("Duplikuj");

        jButton20.setText("Dodaj");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        jButton21.setText("Usuń");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jButton19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton20)
                    .addComponent(jButton21)))
        );

        jTabbedPane1.addTab("Ubiory", jPanel4);

        javax.swing.GroupLayout layersLayout = new javax.swing.GroupLayout(layers);
        layers.setLayout(layersLayout);
        layersLayout.setHorizontalGroup(
            layersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layersLayout.setVerticalGroup(
            layersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        workingarea.setFocusCycleRoot(true);
        workingarea.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                workingareaMouseMoved(evt);
            }
        });
        workingarea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                workingareaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                workingareaMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                workingareaMousePressed(evt);
            }
        });
        workingarea.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                workingareaComponentResized(evt);
            }
        });
        workingarea.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                workingareaPropertyChange(evt);
            }
        });
        workingarea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                workingareaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                workingareaKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout workingareaLayout = new javax.swing.GroupLayout(workingarea);
        workingarea.setLayout(workingareaLayout);
        workingareaLayout.setHorizontalGroup(
            workingareaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        workingareaLayout.setVerticalGroup(
            workingareaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 520, Short.MAX_VALUE)
        );

        lupa_val.setText("0");

        jButton9.setText("-");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setText("+");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jLabel3.setText("X");

        x_param.setText("-1");

        jLabel5.setText("Y");

        y_param.setText("-1");

        mod_ub.setText("Model");
        mod_ub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mod_ubActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(x_param)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(y_param)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(mod_ub)
                .addGap(18, 18, 18)
                .addComponent(lupa_val)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton10)
                .addGap(4, 4, 4)
                .addComponent(jButton9))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lupa_val, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9)
                    .addComponent(jButton10)
                    .addComponent(jLabel3)
                    .addComponent(x_param)
                    .addComponent(jLabel5)
                    .addComponent(y_param)
                    .addComponent(mod_ub)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(timeline, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(toolbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(workingarea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(layers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(layers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(toolbox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(workingarea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(timeline, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        workingarea.getAccessibleContext().setAccessibleName("");
        workingarea.getAccessibleContext().setAccessibleDescription("");

        jMenu1.setText("Plik");

        jMenuItem1.setText("Nowy Projekt");
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Otwórz Projekt");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Zapisz Projekt");
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edycja");

        jMenuItem4.setText("Opcje kartki");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void color1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_color1MouseClicked
        // TODO add your handling code here:
        col1 = JColorChooser.showDialog(
                     this,
                     "Choose Background Color",
                     col1);
        color1.setBackground(col1);
    }//GEN-LAST:event_color1MouseClicked

    private void color2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_color2MouseClicked
        col2 = JColorChooser.showDialog(
                     this,
                     "Choose Background Color",
                     col2);
        color2.setBackground(col2);
    }//GEN-LAST:event_color2MouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        Color tmp1 = col1;
        Color tmp2 = col2;
        col1=tmp2;
        col2=tmp1;
        color1.setBackground(col1);
        color2.setBackground(col2);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void workingareaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_workingareaPropertyChange
        // TODO add your handling code here:
        
    }//GEN-LAST:event_workingareaPropertyChange

    private void workingareaComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_workingareaComponentResized
        // TODO add your handling code here:
        Insets ins = this.getInsets();
        System.out.println(ins.right);
        int x=(this.getSize().width-ins.right-ins.left)/3;
        int y=(this.getSize().height/3)-(sheet.h);
        sheet.setX(x);
        //sheet.setY(y);
        sheet.repaint();
    }//GEN-LAST:event_workingareaComponentResized

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton14ActionPerformed

    private void workingareaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_workingareaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_workingareaKeyTyped

    private void workingareaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_workingareaKeyPressed
        // TODO add your handling code here:
        System.out.println(evt.getKeyCode());
        if(evt.getKeyCode()==38)
        {
            sheet.y--;
            sheet.repaint();
        }else if(evt.getKeyCode()==40)
        {
            sheet.y++;
            sheet.repaint();
        }else if(evt.getKeyCode()==37)
        {
            sheet.x--;
            sheet.repaint();
        }else if(evt.getKeyCode()==39)
        {
            sheet.x++;
            sheet.repaint();
        }
    }//GEN-LAST:event_workingareaKeyPressed

    private void formKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_formKeyTyped

    private void workingareaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_workingareaMouseEntered
        // TODO add your handling code here:
        workingarea.requestFocus();
    }//GEN-LAST:event_workingareaMouseEntered

    private void workingareaMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_workingareaMouseMoved
        // TODO add your handling code here:
        //System.out.println(evt.getX()+" "+ evt.getY());
        int x=evt.getX();
        int y=evt.getY();
        actual_px_x=-1;
        actual_px_y=-1;
        if(sheet.animations.size()>0 && sheet.current_animation<sheet.animations.size())
        {
            //TODO: Moze trzeba bedzie zmienic tak ze kazda klatka ma inna wielkosc
            for(int i=0; i<sheet.animations.get(sheet.current_animation).w; i++)
                for(int j=0; j<sheet.animations.get(sheet.current_animation).h;j++)
                {
                    if(
                       (x>=(sheet.x+i*(sheet.px_size+sheet.percent)) 
                       && 
                       x<=(sheet.x+i*(sheet.px_size+sheet.percent)+(sheet.px_size+sheet.percent)))
                       &&
                       (y>=(sheet.y+j*(sheet.px_size+sheet.percent)) 
                       && 
                       y<=(sheet.y+j*(sheet.px_size+sheet.percent)+(sheet.px_size+sheet.percent)))
                      )
                    {
                        actual_px_x=i;
                        actual_px_y=j;
                        break;
                    }
                }
        }
        
        x_param.setText(""+actual_px_x);
        y_param.setText(""+actual_px_y);
    }//GEN-LAST:event_workingareaMouseMoved

    private void workingareaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_workingareaMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_workingareaMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        tool="pencil";
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        tool="rubber";
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        sheetoptionframe.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void sheetoptionframeWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_sheetoptionframeWindowActivated
        // TODO add your handling code here:
        jTextField1.setText(""+sheet.px_size);
        jTextField2.setText(""+sheet.px_in_world);
        if(sheet.grid==true)
            jCheckBox2.setSelected(true);
        else
            jCheckBox2.setSelected(false);
    }//GEN-LAST:event_sheetoptionframeWindowActivated

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        sheetoptionframe.setVisible(false);
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        //System.out.println(Integer.parseInt(jTextField1.getText()));
        boolean canIClose=false;
        try{
            sheet.px_size=Integer.parseInt(jTextField1.getText());
            jTextField1.setBackground(new Color(255,255,255));
            canIClose = true;
        }catch(NumberFormatException e){
            canIClose = false;
            jTextField1.setBackground(new Color(255,0,0));
        }
        try{
            sheet.px_in_world=Integer.parseInt(jTextField2.getText());
            jTextField1.setBackground(new Color(255,255,255));
            canIClose = true;
        }catch(NumberFormatException e){
            canIClose = false;
            jTextField2.setBackground(new Color(255,0,0));
        }
        if(jCheckBox2.isSelected())
        {
            sheet.grid=true;
        }
        else
        {
            sheet.grid=false;
        }
        if(canIClose==true)
        {
            sheetoptionframe.setVisible(false);
            sheet.repaint();
        }
            
    }//GEN-LAST:event_jButton13ActionPerformed

    private void workingareaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_workingareaMousePressed
        // TODO add your handling code here:
        if(actual_px_x!=-1 && actual_px_y!=-1)
        {
            if(tool.equals("pencil"))
            {

                    if(evt.getButton()==1)
                    {
                        sheet.addPixel(actual_px_x, actual_px_y, col1);
                    }else if(evt.getButton()==3)
                    {
                        sheet.addPixel(actual_px_x, actual_px_y, col2);
                    }

            }else if(tool.equals("rubber"))
            {
                sheet.addPixel(actual_px_x, actual_px_y, null);
            }
        }
    }//GEN-LAST:event_workingareaMousePressed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        sheet.percent++;
        lupa_val.setText(""+sheet.percent);
        sheet.repaint();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        sheet.percent--;
        lupa_val.setText(""+sheet.percent);
        sheet.repaint();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void mod_ubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mod_ubActionPerformed
        // TODO add your handling code here:
        if(mod_ub.getText().equals("Model"))
        {
            sheet.cloth_making=true;
            mod_ub.setText("Ubiór");
        }
        else
        {
            sheet.cloth_making=false;
            mod_ub.setText("Model");
        }
        sheet.repaint();
    }//GEN-LAST:event_mod_ubActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        String s;
        boolean exit_popup=false;
        while(exit_popup==false)
        {
            s = (String)JOptionPane.showInputDialog(
                    this,
                    "Podaj nazwę animacji:",
                    "Dodaj animacje",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null);
            if(s==null)
            {
                break;
            }
            boolean conflict=false;
            for(int i=0; i<sheet.animations.size(); i++)
            {
                if(sheet.animations.get(i).name.equals(s))
                {
                    conflict=true;
                    break;
                }
            }
            if(conflict==false)
            {
                sheet.addAnimation(s);
                exit_popup=true;
            }
        }
        
    }//GEN-LAST:event_jButton8ActionPerformed

    private void anim_listMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_anim_listMouseClicked
        // TODO add your handling code here:
        if(anim_list.getSelectedValue()!=null)
        {
            sheet.selectAnimation(anim_list.getSelectedValue());
            sheet.repaint();
        }
        
    }//GEN-LAST:event_anim_listMouseClicked

    private void cloth_listMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cloth_listMouseClicked
        // TODO add your handling code here:
        if(cloth_list.getSelectedValue()!=null)
        {
            sheet.selectClothing(cloth_list.getSelectedValue());
            sheet.repaint();
        }
    }//GEN-LAST:event_cloth_listMouseClicked

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        // TODO add your handling code here:
        String s;
        boolean exit_popup=false;
        while(exit_popup==false)
        {
            s = (String)JOptionPane.showInputDialog(
                    this,
                    "Podaj nazwę ubioru:",
                    "Dodaj ubiór",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null);
            if(s==null)
            {
                break;
            }
            boolean conflict=false;
            for(int i=0; i<sheet.animations.get(sheet.current_animation).clothing.size(); i++)
            {
                if(sheet.animations.get(sheet.current_animation).clothing.get(i).name.equals(s))
                {
                    conflict=true;
                    break;
                }
            }
            if(conflict==false)
            {
                sheet.addClothing(s);
                exit_popup=true;
            }
        }
    }//GEN-LAST:event_jButton20ActionPerformed

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
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> anim_list;
    private javax.swing.JPanel animation_timeline;
    private javax.swing.JList<String> cloth_list;
    private javax.swing.JPanel color1;
    private javax.swing.JPanel color2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JList<String> jList1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JPanel layers;
    private javax.swing.JLabel lupa_val;
    private javax.swing.JButton mod_ub;
    private javax.swing.JFrame sheetoptionframe;
    private javax.swing.JPanel timeline;
    private javax.swing.JPanel toolbox;
    private javax.swing.JPanel workingarea;
    private javax.swing.JLabel x_param;
    private javax.swing.JLabel y_param;
    // End of variables declaration//GEN-END:variables
}
