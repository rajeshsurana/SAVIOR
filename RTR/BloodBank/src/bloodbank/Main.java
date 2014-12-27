package bloodbank;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;
import java.awt.event.*;
import java.util.*;
/**
 * @author Rtr. Chetan, Rtr. Ganesh, Rtr. Rajesh
 * TEIT
 */
class Culdesac
{
    static int count,dt,mnth,yr;
    static String bb1,bb2,bb3,bb4,bb5;
    static PreparedStatement ca,cs;
    static Connection con;
    static ResultSet rs;
    static
    {
        count=dt=mnth=yr=0;
        bb1=bb2=bb3=bb4=bb5="";
        rs=null;
        try
        {
            Class.forName("org.postgresql.Driver");
            con=DriverManager.getConnection("jdbc:postgresql:postgres","postgres","it");
            ca=con.prepareStatement("insert into culdesac values(?,?,?,?,?,?,?)");
            cs=con.prepareStatement("select count(did) from donor where (dt=? and mnth=? and yr=?)");
        }
        catch(Exception e){}
    }
    
    static void cadd() throws Exception 
    {
        String str=""+dt+"/"+mnth+"/"+yr;
        ca.setString(1,str);
        ca.setInt(2,count);
        ca.setString(3,bb1);
        ca.setString(4,bb2);
        ca.setString(5,bb3);
        ca.setString(6,bb4);
        ca.setString(7,bb5);
        ca.executeUpdate();
    }
    static ResultSet csearch() throws Exception
    {
        cs.setInt(1,dt);
        cs.setInt(2,mnth);
        cs.setInt(3,yr);
        rs=cs.executeQuery();
        return rs;
    }
}

class culdForm extends JFrame implements ActionListener,FocusListener
{
    JLabel date,cnt,bb1,bb2,bb3,bb4,bb5;
    JTextField tcnt,tbb1,tbb2,tbb3,tbb4,tbb5;
    Choice cdt,cmnth,cyr;
    JButton add,close;
    JPanel p,q;

    culdForm(String s,int a,int b,int c,String sbb1)
    {
        super(s);
        // labels
        date=new JLabel("Date :-");
        date.setBounds(70,35,70,30);
        cnt=new JLabel("Count :-");
        cnt.setBounds(70,100,70,30);
        bb1=new JLabel("Blood Bank 1 :-");
        bb1.setBounds(70,170,90,30);
        bb2=new JLabel("Blood Bank 2 :-");
        bb2.setBounds(70,245,90,30);
        bb3=new JLabel("Blood Bank 3 :-");
        bb3.setBounds(70,320,90,30);
        bb4=new JLabel("Blood Bank 4 :-");
        bb4.setBounds(70,395,90,30);
        bb5=new JLabel("Blood Bank 5 :-");
        bb5.setBounds(70,470,90,30);
        
        cdt=new Choice();
        cdt.add("DD");
        cdt.add("01");
        cdt.add("02");
        cdt.add("03");
        cdt.add("04");
        cdt.add("05");
        cdt.add("06");
        cdt.add("07");
        cdt.add("08");
        cdt.add("09");
        for(int i=10;i<=31;i++)
            cdt.add(""+i);
        cdt.setBounds(120,37,50,30);
        if(a!=0 && a<10)
            cdt.select("0"+a);
        else
            cdt.select(""+a);
        cmnth=new Choice();
        cmnth.add("MM");
        cmnth.add("01");
        cmnth.add("02");
        cmnth.add("03");
        cmnth.add("04");
        cmnth.add("05");
        cmnth.add("06");
        cmnth.add("07");
        cmnth.add("08");
        cmnth.add("09");
        for(int i=10;i<=12;i++)
            cmnth.add(""+i);
        cmnth.setBounds(180,37,50,30);
        if(b!=0 && b<10)
            cmnth.select("0"+b);
        else
            cmnth.select(""+b);
        cyr=new Choice();
        cyr.add("YYYY");
        for(int i=1975;i<=2100;i++)
            cyr.add(""+i);
        cyr.setBounds(240,37,90,30);
        if(c!=0)
            cyr.select(""+c);
        tcnt=new JTextField();
        tcnt.setBounds(130,101,50,25);
        tcnt.addFocusListener(this);
        tcnt.setEditable(false);
        tbb1=new JTextField();
        tbb1.setBounds(165,172,200,25);
        tbb1.addActionListener(this);
        tbb1.setText(sbb1);
        tbb2=new JTextField();
        tbb2.setBounds(165,246,200,25);
        tbb2.addActionListener(this);
        tbb3=new JTextField();
        tbb3.setBounds(165,322,200,25);
        tbb3.addActionListener(this);
        tbb4=new JTextField();
        tbb4.setBounds(165,396,200,25);
        tbb4.addFocusListener(this);
        tbb5=new JTextField();
        tbb5.setBounds(165,472,200,25);
        tbb5.addActionListener(this);

        add(date);add(cdt);add(cmnth);add(cyr);
        add(cnt);add(tcnt);
        add(bb1);add(tbb1);
        add(bb2);add(tbb2);
        add(bb3);add(tbb3);
        add(bb4);add(tbb4);
        add(bb5);add(tbb5);
        
        add=new JButton("ADD");
        add.addActionListener(this);
        close=new JButton("CLOSE");
        close.addActionListener(this);
        q=new JPanel();
        p=new JPanel();
        q.setLayout(new GridLayout(5,4,70,70));
        p.add(add);
        p.add(close);
        p.setLayout(new GridLayout(1,4,5,5));

        add(q,BorderLayout.CENTER);
        add(p,BorderLayout.SOUTH);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing()
            {
                setVisible(false);
            }
        });
        setBounds(430,50,520,600);
        setVisible(true);
    }
    public void focusGained(FocusEvent e)
    {
        JTextField t=(JTextField)e.getSource();
        if(t==tcnt)
        {
            Culdesac.dt=Integer.parseInt(cdt.getSelectedItem());
            Culdesac.mnth=Integer.parseInt(cmnth.getSelectedItem());
            Culdesac.yr=Integer.parseInt(cyr.getSelectedItem());
            ResultSet rs1=null;
            try
            {
                rs1=Culdesac.csearch();
                if(rs1.next())
                {
                    tcnt.setText(""+rs1.getInt(1));
                }
            }
            catch(Exception e34){}
        }
    }

    public void focusLost(FocusEvent e){}

    public void itemStateChanged(ItemEvent n){}

    public void actionPerformed(ActionEvent e)
    {
        JButton b=(JButton)e.getSource();
        try
        {
            Culdesac.dt=Integer.parseInt(cdt.getSelectedItem());
            Culdesac.mnth=Integer.parseInt(cmnth.getSelectedItem());
            Culdesac.yr=Integer.parseInt(cyr.getSelectedItem());
            Culdesac.count=Integer.parseInt(tcnt.getText());
        }
        catch(Exception ew)
        {}
        Culdesac.bb1=tbb1.getText();
        Culdesac.bb2=tbb2.getText();
        Culdesac.bb3=tbb3.getText();
        Culdesac.bb4=tbb4.getText();
        Culdesac.bb5=tbb5.getText();
        if(b==add)
        {
            if(cdt.getSelectedItem().equals("DD")||cmnth.getSelectedItem().equals("MM")||cyr.getSelectedItem().equals("YYYY")||tcnt.getText().equals("")||bb1.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null,"All FIELDS are COMPULSORY.");
                return;
            }
            if(tcnt.getText().equals("0"))
            {
                JOptionPane.showMessageDialog(null,"Invalid Summary Record.");
                tcnt.setText("");
                tbb1.setText("");
                tbb2.setText("");
                tbb3.setText("");
                tbb4.setText("");
                tbb5.setText("");
                cdt.requestFocus();
                return;
            }
            try
            {
                Culdesac.cadd();
            }
            catch(Exception e1){}
        }
        if(b==close)
        {
            int opn=0;
            String []opt={"NO","YES"};
            while(true)
            {
                opn=JOptionPane.showOptionDialog(null,"Are you sure you want to quit the Summary Form and return to Main Menu?","Confirmation",JOptionPane.NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,opt,0);
                if(opn==0)
                    break;
                else
                {
                    setVisible(false);
                    break;
                }
            }
        }
        cdt.select("");
        cmnth.select("");
        cyr.select("");
        tcnt.setText("");
        tbb1.setText("");
        tbb2.setText("");
        tbb3.setText("");
        tbb4.setText("");
        tbb5.setText("");
        cdt.requestFocus();
    }
}

// Donor Entry Form
class Donor
{
    static int did,id,age,wt,cnt,dt,mnth,yr,admyr;
    static String name,blg,cno,email,gen,bb;
    static PreparedStatement da,du,ds,dfid,dab,dfid1;
    static ResultSet rs;
    static Connection con;

    static
    {
        did=id=age=wt=dt=mnth=yr=cnt=admyr=0;
        name=blg=email=gen=cno=bb="";
        rs=null;
        try
        {
            Class.forName("org.postgresql.Driver");
            con=DriverManager.getConnection("jdbc:postgresql:postgres","postgres","it");
            da=con.prepareStatement("insert into donor values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
            du=con.prepareStatement("Update donor set did=?,blg=?,age=?,wt=?,cno=?,email=?,admyr=?,gen=?,cnt=?,dt=?,mnth=?,yr=? where name=?");
            ds=con.prepareStatement("select * from donor where name=?");
            dfid=con.prepareStatement("select max(did) from donor");
            dab=con.prepareStatement("insert into bbinfo values(?,?,?)");
            dfid1=con.prepareStatement("select max(id) from bbinfo");
        }
        catch(Exception e1){}
    }

    static void dadd() throws Exception
    {
        da.setInt(1,did);
        da.setString(2,name);
        da.setString(3,blg);
        da.setInt(4,age);
        da.setInt(5,wt);
        da.setString(6,cno);
        da.setString(7,email);
        da.setString(8,gen);
        da.setInt(9,dt);
        da.setInt(10,mnth);
        da.setInt(11,yr);
        da.setInt(12,admyr);
        da.setInt(13,cnt);
        da.executeUpdate();
    }

    static void dmod() throws Exception
    {
        du.setInt(1,did);
        du.setString(2,blg);
        du.setInt(3,age);
        du.setInt(4,wt);
        du.setString(5,cno);
        du.setString(6,email);
        du.setInt(7,admyr);
        du.setString(8,gen);
        du.setInt(9,cnt);
        du.setInt(10,dt);
        du.setInt(11,mnth);
        du.setInt(12,yr);
        du.setString(13,name);
        du.executeUpdate();
    }

    static boolean dsearch(String str) throws Exception
    {
        ds.setString(1,str);
        rs=ds.executeQuery();
        return rs.next();
    }

    static ResultSet dFindId() throws Exception
    {
        rs=dfid.executeQuery();
        return rs;
    }

    static void badd() throws Exception
    {
        dab.setInt(1,id);
        dab.setInt(2,did);
        dab.setString(3,bb);
        dab.executeUpdate();
    }

    static ResultSet dFindId1() throws Exception
    {
        rs=dfid1.executeQuery();
        return rs;
    }
}

class donForm extends JFrame implements ActionListener,FocusListener,ItemListener
{
    JLabel head,did,dnm,date,blg,gen,age,wt,admyr,cno,email,cnt,bb;
    JTextField tid,tnm,tage,twt,tcno,temail,tcnt,tbb;
    Choice cdt,cmnth,cyr,cblg,cdom,cadmyr;
    JButton ba,bu,rst,close,over;
    JPanel p,q,r;
    CheckboxGroup gn;
    Checkbox ml,fl;

    donForm(String s)
    {
        super(s);
        // labels
        dnm=new JLabel("Name :-");
        dnm.setBounds(90,55,70,30);
        did=new JLabel("Donor ID :-");
        did.setBounds(90,130,70,30);
        date=new JLabel("Date :-");
        date.setBounds(340,130,70,30);
        blg=new JLabel("Blood Group :-");
        blg.setBounds(90,205,90,30);
        gen=new JLabel("Gender :-");
        gen.setBounds(340,205,70,30);
        age=new JLabel("Age :-");
        age.setBounds(90,280,70,30);
        wt=new JLabel("Weight :-");
        wt.setBounds(340,280,70,30);
        admyr=new JLabel("Admission Year :-");
        admyr.setBounds(90,355,100,30);
        cno=new JLabel("Contact NO :-");
        cno.setBounds(90,430,90,30);
        email=new JLabel("E-mail Address :-");
        email.setBounds(90,505,100,30);
        cnt=new JLabel("Frequency :-");
        cnt.setBounds(90,580,70,30);
        head=new JLabel("Donor Form :-");
        bb=new JLabel("BloodBank :-");
        bb.setBounds(340,355,100,30);

        //panels
        p=new JPanel();
        q=new JPanel();
        r=new JPanel();
        p.add(head);
        
        // Textfield
        tnm=new JTextField();
        tnm.setBounds(145,60,490,23);
        tid=new JTextField();
        tid.setBounds(160,135,60,23);
        tid.setEditable(false);
        tage=new JTextField();
        tage.setBounds(135,285,60,23);
        twt=new JTextField();
        twt.setBounds(405,285,65,23);
        tcno=new JTextField();
        tcno.setBounds(175,435,250,23);
        temail=new JTextField();
        temail.setBounds(195,509,250,23);
        tcnt=new JTextField();
        tcnt.setBounds(170,585,60,23);
        tcnt.setEditable(false);
        tbb=new JTextField();
        tbb.setBounds(422,359,215,23);

        // Checkbox
        gn=new CheckboxGroup();
        ml=new Checkbox("Male",true,gn);
        ml.setBounds(405,200,70,40);
        fl=new Checkbox("Female",true,gn);
        fl.setBounds(475,200,90,40);

        // here are the choices
        cdt=new Choice();
        cdt.add("DD");
        cdt.add("01");
        cdt.add("02");
        cdt.add("03");
        cdt.add("04");
        cdt.add("05");
        cdt.add("06");
        cdt.add("07");
        cdt.add("08");
        cdt.add("09");
        for(int i=10;i<=31;i++)
            cdt.add(""+i);
        cdt.setBounds(390,135,50,35);
        cmnth=new Choice();
        cmnth.add("MM");
        cmnth.add("01");
        cmnth.add("02");
        cmnth.add("03");
        cmnth.add("04");
        cmnth.add("05");
        cmnth.add("06");
        cmnth.add("07");
        cmnth.add("08");
        cmnth.add("09");
        for(int i=10;i<=12;i++)
            cmnth.add(""+i);
        cmnth.setBounds(460,135,50,35);
        cyr=new Choice();
        cyr.add("YYYY");
        for(int i=1975;i<=2100;i++)
            cyr.add(""+i);
        cyr.setBounds(530,135,100,35);
        cblg=new Choice();
        cblg.add("Blood Group");
        cblg.add("A+");
        cblg.add("A-");
        cblg.add("B+");
        cblg.add("B-");
        cblg.add("AB+");
        cblg.add("AB-");
        cblg.add("O+");
        cblg.add("O-");
        cblg.setBounds(180,209,100,35);
        cadmyr=new Choice();
        cadmyr.add("YYYY");
        for(int i=1975;i<=2100;i++)
            cadmyr.add(""+i);
        cadmyr.setBounds(205,359,100,35);
        cdom=new Choice();
        cdom.add("Domain Name");
        cdom.add("@gmail.com");
        cdom.add("@rediff.com");
        cdom.add("@gmail.co.in");
        cdom.add("@yahoo.com");
        cdom.add("@hotmail.com");
        cdom.add("");
        cdom.setBounds(455,509,120,40);
 
        //added to the panel
        add(dnm);add(tnm);
        add(did);add(tid);add(date);add(cdt);add(cmnth);add(cyr);
        add(blg);add(cblg);add(gen);add(ml);add(fl);
        add(age);add(tage); add(wt);add(twt);
        add(admyr);add(cadmyr); add(bb);add(tbb);
        add(cno);add(tcno);
        add(email);add(temail); add(cdom);
        add(cnt);add(tcnt);

        q.setLayout(new GridLayout(5,4,70,70));
        tid.addFocusListener(this);
        tnm.addFocusListener(this);
        tage.addFocusListener(this);
        twt.addFocusListener(this);
        tcno.addFocusListener(this);
        temail.addFocusListener(this);
        tcnt.addFocusListener(this);
        
        //buttons
        ba=new JButton("ADD");
        bu=new JButton("UPDATE");
        rst=new JButton("RESET");
        close=new JButton("MAIN MENU");
        over=new JButton("CUL-DE-SAC");

        ba.addActionListener(this);
        bu.addActionListener(this);
        rst.addActionListener(this);
        close.addActionListener(this);
        over.addActionListener(this);
        r.add(ba);
        r.add(bu);
        r.add(rst);
        r.add(over);
        r.setLayout(new GridLayout(1,4,5,5));
        add(p,BorderLayout.NORTH);
        add(q,BorderLayout.CENTER);
        add(r,BorderLayout.SOUTH);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing()
            {
                setVisible(false);
            }
        });
        setBounds(100,10,800,700);
        setVisible(true);
    }

    public void focusGained(FocusEvent e)
    {
        JTextField t=(JTextField)e.getSource();
        String s=tnm.getText();
        if(t==tid)
        {
            boolean res=false;
            try
            {
                res=Donor.dsearch(s);
            }
            catch(Exception er)
            {}
            if(res==false)
            {
                try
                {
                    ResultSet rs1=Donor.dFindId();
                    if(rs1.next())
                    tid.setText(""+(rs1.getInt(1)+1));
                }
                catch(Exception we)
                {}
            }
        }
        if(t==tcnt)
        {
            try
            {
                if(!(Donor.dsearch(tnm.getText())))
                {
                    tcnt.setText(""+1);
                }
            }
            catch(Exception ee){}
        }
    }

    public void focusLost(FocusEvent e)
    {
        JTextField t=(JTextField)e.getSource();
        if(t==tnm)
        {
            int flg=0;
            String s=tnm.getText();
            char a[]=null;
            a=s.toCharArray();
            int len=s.length();
            for(int i=0;i<len;i++)
            {
                if(!((a[i]>='A'&&a[i]<='Z')||(a[i]>='a'&&a[i]<='z')||(a[i]==32)))
                {
                    flg=1;
                    break;
                }
            }
            if(flg==1)
            {
                JOptionPane.showMessageDialog(null,"Invalid Name.");
                tnm.setText("");
                tnm.requestFocus();
            }
            boolean res=false;
            try
            {
                res=Donor.dsearch(s);
            }
            catch(Exception e2){}
            if(res)
            {
                try
                {
                    tid.setText(""+Donor.rs.getInt(1));
                    tage.setText(""+Donor.rs.getInt(4));
                    twt.setText(""+Donor.rs.getInt(5));
                    tcno.setText(Donor.rs.getString(6));

                    String s1=Donor.rs.getString(7);
                    String s2="",s3="";
                    char ch[]=null;
                    int l=s1.length();
                    for(int i=1;i<l;i++)
                    {
                        if(s1.charAt(i)=='@')
                        {
                            s3=s1.substring(i,l);
                            s2=s1.substring(0,i);
                            break;
                        }
                    }
                    temail.setText(s2);
                    cdom.select(s3);
                    tcnt.setText(""+(Donor.rs.getInt(10)+1));
                    if(Donor.rs.getString(9).equals("Male"))
                        gn.setSelectedCheckbox(ml);
                    else
                        gn.setSelectedCheckbox(fl);
                    cblg.select(Donor.rs.getString(3));
                    cadmyr.select(Donor.rs.getString(8));
                }
                catch(Exception e3){}
                ba.setEnabled(false);
                bu.setEnabled(true);
            }
            else
            {
                ba.setEnabled(true);
                bu.setEnabled(false);
            }
        }
        if(t==tcno)
        {
            int flg=0;
            String s=tcno.getText();
            char a[]=null;
            a=s.toCharArray();
            int len=s.length();
            for(int i=0;i<len;i++)
            {
                if((a[i]>='A'&&a[i]<='Z')||(a[i]>='a'&&a[i]<='z'))
                {
                    flg=1;
                    break;
                }
            }
            if(flg==1)
            {
                JOptionPane.showMessageDialog(null,"Invalid Number.");
                tcno.setText("");
                tcno.requestFocus();
            }
        }
        if(t==tage)
        {
            int a;
            try
            {
                a=Integer.parseInt(tage.getText());
            }
            catch(Exception e1)
            {
                a=18;
            }
            if(a<18)
            {
                JOptionPane.showMessageDialog(null,"Invalid Age. Age should be greater than 17.");
                tage.setText("");
                tage.requestFocus();
            }
        }
        if(t==twt)
        {
            int a;
            try
            {
                a=Integer.parseInt(twt.getText());
            }
            catch(Exception e1)
            {
                a=50;
            }
            if(a<50)
            {
                JOptionPane.showMessageDialog(null,"Invalid Weight. Wieght should be greater than 50.");
                twt.setText("");
                twt.requestFocus();
            }
        }
    }

    public void itemStateChanged(ItemEvent n){}

    public void actionPerformed(ActionEvent e)
    {
        String []opt={"NO","YES"};
        int opn=0;
        JButton b=(JButton)e.getSource();
        Checkbox g=gn.getSelectedCheckbox();
        try
        {
            ResultSet rs1=Donor.dFindId1();
            if(rs1.next())
                Donor.id=rs1.getInt(1)+1;
            Donor.name=tnm.getText();
            Donor.did=Integer.parseInt(tid.getText());
            Donor.dt=Integer.parseInt(cdt.getSelectedItem());
            Donor.mnth=Integer.parseInt(cmnth.getSelectedItem());
            Donor.yr=Integer.parseInt(cyr.getSelectedItem());
            Donor.blg=cblg.getSelectedItem();
            Donor.gen=g.getLabel();
            Donor.age=Integer.parseInt(tage.getText());
            Donor.wt=Integer.parseInt(twt.getText());
            Donor.admyr=Integer.parseInt(cadmyr.getSelectedItem());
            Donor.cno=tcno.getText();
            Donor.email=temail.getText()+cdom.getSelectedItem();
            Donor.cnt=Integer.parseInt(tcnt.getText());
            Donor.bb=tbb.getText();
        }
        catch(Exception e4){}
        if(b==ba)
        {
            if(tnm.getText().equals("")||twt.getText().equals("")||cblg.getSelectedItem().equals("Blood Group")||cdt.getSelectedItem().equals("DD")||cmnth.getSelectedItem().equals("MM")||cyr.getSelectedItem().equals("YYYY")||cadmyr.getSelectedItem().equals("YYYY")||tage.getText().equals("")||tcno.getText().equals("")||temail.getText().equals("")||tcnt.getText().equals("")||tbb.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null,"All FIELDS are COMPULSORY.");
                tnm.requestFocus();
                return;
            }
            try
            {
                Donor.dadd();
                Donor.badd();
            }
            catch(Exception e1){}
        }
        if(b==bu)
        {
            if(tnm.getText().equals("")||twt.getText().equals("")||cblg.getSelectedItem().equals("Blood Group")||cdt.getSelectedItem().equals("DD")||cmnth.getSelectedItem().equals("MM")||cyr.getSelectedItem().equals("YYYY")||cadmyr.getSelectedItem().equals("YYYY")||tage.getText().equals("")||tcno.getText().equals("")||temail.getText().equals("")||tcnt.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null,"All FIELDS are COMPULSORY.");
                tnm.requestFocus();
                return;
            }
            try
            {
                Donor.dmod();
                Donor.badd();
            }
            catch(Exception e2){}
        }
        if(b==rst)
        {
            tnm.setText("");
            tid.setText("");
            cblg.select("Blood Group");
            ml.setState(true);
            tage.setText("");
            twt.setText("");
            tcno.setText("");
            temail.setText("");
            cdom.select("Domain Name");
            tcnt.setText("");
            tbb.setText("");
            tnm.requestFocus();
        }
        if(b==close)
        {
            while(true)
            {
                opn=JOptionPane.showOptionDialog(null,"Are you sure you want to quit the Entry Form and return to Main Menu?","Confirmation",JOptionPane.NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,opt,0);
                if(opn==0)
                    break;
                else
                {
                    setVisible(false);
                    break;
                }
            }
        }
        if(b==over)
        {
            culdForm e4;
            while(true)
            {
                opn=JOptionPane.showOptionDialog(null,"Are you sure you want to quit the Entry Form and write the Summary?","Confirmation",JOptionPane.YES_OPTION,JOptionPane.INFORMATION_MESSAGE,null,opt,0);
                if(opn==0)
                    break;
                else
                {
                    setVisible(false);
                    int dd,mm,yyyy;
                    String bb="";
                    try
                    {
                        dd=Integer.parseInt(cdt.getSelectedItem());
                        mm=Integer.parseInt(cmnth.getSelectedItem());
                        yyyy=Integer.parseInt(cyr.getSelectedItem());
                        bb=tbb.getText();
                    }
                    catch(Exception ew)
                    {
                        dd=0;
                        mm=0;
                        yyyy=0;
                        bb="";
                    }
                    e4=new culdForm("SUMMARY",dd,mm,yyyy,bb);
                    break;
                }
            }
        }
        tnm.setText("");
        tid.setText("");
        cblg.select("Blood Group");
        ml.setState(true);
        tage.setText("");
        twt.setText("");
        tcno.setText("");
        temail.setText("");
        tcnt.setText("");
        cdom.select("Domain Name");
        tnm.requestFocus();
    }
}

class ColumnSorter implements Comparator
{
    int colIndex;
    boolean ascending;

    ColumnSorter(int colIndex, boolean ascending)
    {
        this.colIndex = colIndex;
        this.ascending = ascending;
    }

    public int compare(Object a, Object b)
    {
        Vector v1 = (Vector)a;
        Vector v2 = (Vector)b;
        Object o1 = v1.get(colIndex);
        Object o2 = v2.get(colIndex);
        // Treat empty strains like nulls
        if(o1 instanceof String && ((String)o1).length() == 0)
        {
            o1 = null;
        }
        if(o2 instanceof String && ((String)o2).length() == 0)
        {
            o2 = null;
        }
        // Sort nulls so they appear last, regardless
        // of sort order
        if(o1==null && o2==null)
        {
            return 0;
        }
        else if(o1 == null)
        {
            return 1;
        }
        else if (o2 == null)
        {
            return -1;
        }
        else if (o1 instanceof Comparable)
        {
            if(ascending)
            {
                return ((Comparable)o1).compareTo(o2);
            }
            else
            {
                return ((Comparable)o2).compareTo(o1);
            }
        }
        else
        {
            if(ascending)
            {
                return o1.toString().compareTo(o2.toString());
            }
            else
            {
                return o2.toString().compareTo(o1.toString());
            }
        }
    }
}

class modify
{
    static int did,id,age,wt,cnt,dt,mnth,yr,admyr;
    static String name,blg,cno,email,gen,bb;
    static PreparedStatement dm,ds;
    static ResultSet rs;
    static Connection con;

    static
    {
        did=id=age=wt=dt=mnth=yr=cnt=admyr=0;
        name=blg=email=gen=cno=bb="";
        rs=null;
        try
        {
            Class.forName("org.postgresql.Driver");
            con=DriverManager.getConnection("jdbc:postgresql:postgres","postgres","it");
            ds=con.prepareStatement("select * from donor where did=?");
            dm=con.prepareStatement("Update donor set name=?,blg=?,age=?,wt=?,cno=?,email=?,admyr=?,gen=? where did=?");
        }
        catch(Exception e1){}
    }

    static ResultSet disp() throws Exception
    {
        ds.setInt(1,did);
        rs=ds.executeQuery();
        return rs;
    }

    static void dmod() throws Exception
    {
        dm.setString(1,name);
        dm.setString(2,blg);
        dm.setInt(3,age);
        dm.setInt(4,wt);
        dm.setString(5,cno);
        dm.setString(6,email);
        dm.setInt(7,admyr);
        dm.setString(8,gen);
        dm.setInt(9,did);
        dm.executeUpdate();
    }
}

class panel1 extends JPanel implements ActionListener, FocusListener
{
    DefaultTableCellRenderer renderer;
    JSplitPane jsp;
    ResultSet k;
    JTable jt;
    JScrollPane js;
    Dimension d,d1;
    JPanel p,q;
    JLabel did,name,dt;
    JTextField tdid,tnm,tdt;
    JButton mod,close,rfrsh;
    String a,b,c;
    modForm m;

    panel1(modForm m1)
    {
        renderer=new DefaultTableCellRenderer();
        m=m1;
        setLayout(null);
        jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        jt=new JTable();
        k=null;
        String [][]arr=new String[300][11];
        String []chd={"ID","Name","BloodGr","Date","Age","Weight","Gender","Frequency","Adm. Year","Contact No","E-mail"};
        js=new JScrollPane(jt);
        int i=0;
        try
        {
            k=delete.disp();
            while(k.next())
            {
                arr[i][0]=""+k.getInt(1);
                arr[i][1]=k.getString(2);
                arr[i][2]=k.getString(3);
                arr[i][3]=""+k.getInt(9)+"/"+k.getInt(10)+"/"+k.getInt(11);
                arr[i][4]=""+k.getInt(4);
                arr[i][5]=""+k.getInt(5);
                arr[i][6]=k.getString(8);
                arr[i][7]=""+k.getInt(13);
                arr[i][8]=""+k.getInt(12);
                arr[i][9]=k.getString(6);
                arr[i][10]=k.getString(7);
                i++;
            }
        }
        catch(Exception ew){}
        DefaultTableModel model=new DefaultTableModel(arr,chd);
        jt.setModel(model);
        setTableCellAlignment(0);
        jt.setAutoCreateColumnsFromModel(false);
        sortAllRowsBy(model,0,true);

        p=new JPanel();
        p.setLayout(null);
        q=new JPanel();
        did=new JLabel("Donor ID :-");
        did.setBounds(226,30,60,30);
        name=new JLabel("Name :-");
        name.setBounds(239,70,60,30);
        dt=new JLabel("Date :-");
        dt.setBounds(245,110,60,30);
        tdid=new JTextField();
        tdid.setBounds(288,35,60,23);
        tdid.addFocusListener(this);
        tdid.setEditable(false);
        tnm=new JTextField();
        tnm.setBounds(288,75,390,23);
        tnm.addFocusListener(this);
        tnm.setEditable(false);
        tdt=new JTextField();
        tdt.setBounds(288,115,150,23);
        tdt.addFocusListener(this);
        tdt.setEditable(false);
        p.add(did);p.add(tdid);
        p.add(name);p.add(tnm);
        p.add(dt);p.add(tdt);
        mod=new JButton("MODIFY ENTRY");
        mod.addActionListener(this);
        mod.setBounds(150,175,220,28);
        close=new JButton("CLOSE");
        close.addActionListener(this);
        close.setBounds(380,175,220,28);
        rfrsh=new JButton("REFRESH");
        rfrsh.addActionListener(this);
        rfrsh.setBounds(610,175,220,28);
        p.add(mod);p.add(close);p.add(rfrsh);

        d=new Dimension(600,500);
        d1=new Dimension(600,200);
        jsp.setTopComponent(js);
        js.setMinimumSize(d);
        p.setMinimumSize(d1);
        jsp.setBottomComponent(p);
        jsp.setBounds(0,0,1000,700);
        add(jsp);
    }

    void setTableCellAlignment(int alignment)
    {
        renderer.setHorizontalAlignment(alignment);
        for(int i=0;i<jt.getColumnCount();i++)
        {
            jt.setDefaultRenderer(jt.getColumnClass(i),renderer);
        }
        // repaint to show table cell changes
        jt.updateUI();
     }

    public void sortAllRowsBy(DefaultTableModel model, int colIndex, boolean ascending)
    {
        Vector data = model.getDataVector();
        Collections.sort(data, new ColumnSorter(colIndex, ascending));
        model.fireTableStructureChanged();
    }

    public void actionPerformed(ActionEvent e)
    {
        JButton b=(JButton)e.getSource();
        if(b==mod)
        {
            modify.did=Integer.parseInt(tdid.getText());
            try
            {
                k=modify.disp();
                while(k.next())
                {
                    modify.name=k.getString(2);
                    modify.blg=k.getString(3);
                    modify.age=k.getInt(4);
                    modify.wt=k.getInt(5);
                    modify.cno=k.getString(6);
                    modify.email=k.getString(7);
                    modify.admyr=k.getInt(12);
                    modify.gen=k.getString(8);
                    modify.cnt=k.getInt(13);
                    modify.dt=k.getInt(9);
                    modify.mnth=k.getInt(10);
                    modify.yr=k.getInt(11);
                }
            }
            catch(Exception ew)
            {}
        }
        if(b==close)
        {
            int opn=0;
            String []opt={"NO","YES"};
            while(true)
            {
                opn=JOptionPane.showOptionDialog(null,"Are you sure you want to quit the MODIFY ENTRY FORM?","Confirmation",JOptionPane.NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,opt,0);
                if(opn==0)
                    break;
                else
                {
                    m.setVisible(false);
                    break;
                }
            }
        }
        if(b==rfrsh)
        {
            m.setVisible(false);
            modForm mf=new modForm("MODIFY ENTRY");
        }
        tdid.setText("");
        tnm.setText("");
        tdt.setText("");
    }

    public void focusGained(FocusEvent e)
    {
        JTextField t=(JTextField)e.getSource();
        try
        {
            a=""+jt.getModel().getValueAt(jt.getSelectedRow(),0);
            b=""+jt.getModel().getValueAt(jt.getSelectedRow(),1);
            c=""+jt.getModel().getValueAt(jt.getSelectedRow(),3);
        }
        catch(Exception ew)
        {
            a="";
            b="";
            c="";
        }
        if(t==tnm)
        {
            tdid.setText(a);
            tnm.setText(b);
            tdt.setText(c);
        }
        if(t==tdid)
        {
            tdid.setText(a);
            tnm.setText(b);
            tdt.setText(c);
        }
        if(t==tdt)
        {
            tdid.setText(a);
            tnm.setText(b);
            tdt.setText(c);
        }
    }

    public void focusLost(FocusEvent e)
    {}
}

class panel2 extends JPanel implements ActionListener, FocusListener, ItemListener
{
    JLabel head,did,dnm,date,blg,gen,age,wt,admyr,cno,email,cnt;
    JTextField tid,tnm,tage,twt,tcno,temail,tcnt;
    Choice cdt,cmnth,cyr,cblg,cdom,cadmyr;
    CheckboxGroup gn;
    Checkbox ml,fl;
    JButton mod,close,rfrsh;
    modForm m;

    panel2(modForm m2)
    {
        m=m2;
        setLayout(null);
        dnm=new JLabel("Name :-");
        dnm.setBounds(90,35,70,30);
        did=new JLabel("Donor ID :-");
        did.setBounds(90,100,70,30);
        date=new JLabel("Date :-");
        date.setBounds(340,100,70,30);
        blg=new JLabel("Blood Group :-");
        blg.setBounds(90,165,90,30);
        gen=new JLabel("Gender :-");
        gen.setBounds(340,165,70,30);
        age=new JLabel("Age :-");
        age.setBounds(90,240,70,30);
        wt=new JLabel("Weight :-");
        wt.setBounds(340,240,70,30);
        admyr=new JLabel("Admission Year :-");
        admyr.setBounds(90,315,100,30);
        cno=new JLabel("Contact NO :-");
        cno.setBounds(90,390,90,30);
        email=new JLabel("E-mail Address :-");
        email.setBounds(90,465,100,30);
        cnt=new JLabel("Frequency :-");
        cnt.setBounds(90,530,70,30);

        // Textfield
        tnm=new JTextField();
        tnm.setBounds(145,40,490,23);
        tid=new JTextField();
        tid.setBounds(160,105,60,23);
        tid.setEditable(false);
        tage=new JTextField();
        tage.setBounds(135,245,60,23);
        twt=new JTextField();
        twt.setBounds(405,245,65,23);
        tcno=new JTextField();
        tcno.setBounds(175,395,250,23);
        temail=new JTextField();
        temail.setBounds(195,469,250,23);
        tcnt=new JTextField();
        tcnt.setBounds(170,535,60,23);
        tcnt.setEditable(false);

        // Checkbox
        gn=new CheckboxGroup();
        ml=new Checkbox("Male",true,gn);
        ml.setBounds(405,160,70,40);
        fl=new Checkbox("Female",true,gn);
        fl.setBounds(475,160,90,40);

        // here are the choices
        cdt=new Choice();
        cdt.setEnabled(false);
        cdt.add("DD");
        for(int i1=1;i1<=31;i1++)
            cdt.add(""+i1);
        cdt.setBounds(390,105,50,35);
        cmnth=new Choice();
        cmnth.setEnabled(false);
        cmnth.add("MM");
        for(int i2=1;i2<=12;i2++)
            cmnth.add(""+i2);
        cmnth.setBounds(460,105,50,35);
        cyr=new Choice();
        cyr.setEnabled(false);
        cyr.add("YYYY");
        for(int i3=1975;i3<=2100;i3++)
            cyr.add(""+i3);
        cyr.setBounds(530,105,100,35);
        cblg=new Choice();
        cblg.add("Blood Group");
        cblg.add("A+");
        cblg.add("A-");
        cblg.add("B+");
        cblg.add("B-");
        cblg.add("AB+");
        cblg.add("AB-");
        cblg.add("O+");
        cblg.add("O-");
        cblg.setBounds(180,169,100,35);
        cadmyr=new Choice();
        cadmyr.add("YYYY");
        for(int i4=1975;i4<=2100;i4++)
            cadmyr.add(""+i4);
        cadmyr.setBounds(205,319,100,35);
        cdom=new Choice();
        cdom.setEnabled(true);
        cdom.add("Domain Name");
        cdom.add("@gmail.com");
        cdom.add("@rediff.com");
        cdom.add("@gmail.co.in");
        cdom.add("@yahoo.com");
        cdom.add("@hotmail.com");
        cdom.add("");
        cdom.setBounds(455,469,120,40);

        mod=new JButton("MODIFY ENTRY");
        mod.addActionListener(this);
        mod.setBounds(150,605,220,28);
        close=new JButton("CLOSE");
        close.addActionListener(this);
        close.setBounds(380,605,220,28);
        rfrsh=new JButton("REFRESH");
        rfrsh.addActionListener(this);
        rfrsh.setBounds(610,605,220,28);
        add(mod);add(close);add(rfrsh);

        tid.addFocusListener(this);
        tnm.addFocusListener(this);
        tage.addFocusListener(this);
        twt.addFocusListener(this);
        tcno.addFocusListener(this);
        temail.addFocusListener(this);
        tcnt.addFocusListener(this);

        //added to the panel
        add(dnm);add(tnm);
        add(did);add(tid);add(date);add(cdt);add(cmnth);add(cyr);
        add(blg);add(cblg);add(gen);add(ml);add(fl);
        add(age);add(tage); add(wt);add(twt);
        add(admyr);add(cadmyr); 
        add(cno);add(tcno);
        add(email);add(temail); add(cdom);
        add(cnt);add(tcnt);
        add(mod);add(close);
    }

    public void actionPerformed(ActionEvent e)
    {
        JButton b=(JButton)e.getSource();
        Checkbox g=gn.getSelectedCheckbox();
        try
        {
            modify.name=tnm.getText();
            modify.did=Integer.parseInt(tid.getText());
            modify.dt=Integer.parseInt(cdt.getSelectedItem());
            modify.mnth=Integer.parseInt(cmnth.getSelectedItem());
            modify.yr=Integer.parseInt(cyr.getSelectedItem());
            modify.blg=cblg.getSelectedItem();
            modify.gen=g.getLabel();
            modify.age=Integer.parseInt(tage.getText());
            modify.wt=Integer.parseInt(twt.getText());
            modify.admyr=Integer.parseInt(cadmyr.getSelectedItem());
            modify.cno=tcno.getText();
            modify.email=temail.getText()+cdom.getSelectedItem();
            modify.cnt=Integer.parseInt(tcnt.getText());
        }
        catch(Exception e4){}
        if(b==mod)
        {
            if(tnm.getText().equals("")||twt.getText().equals("")||cblg.getSelectedItem().equals("Blood Group")||cdt.getSelectedItem().equals("DD")||cmnth.getSelectedItem().equals("MM")||cyr.getSelectedItem().equals("YYYY")||cadmyr.getSelectedItem().equals("YYYY")||tage.getText().equals("")||tcno.getText().equals("")||temail.getText().equals("")||tcnt.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null,"All FIELDS are COMPULSORY.");
                tnm.requestFocus();
                return;
            }
            try
            {
                modify.dmod();
                modify.name="";
                modify.did=Integer.parseInt("");
                modify.dt=Integer.parseInt("");
                modify.mnth=Integer.parseInt("");
                modify.yr=Integer.parseInt("");
                modify.blg="";
                modify.gen="";
                modify.age=Integer.parseInt("");
                modify.wt=Integer.parseInt("");
                modify.admyr=Integer.parseInt("");
                modify.cno="";
                modify.email="";
                modify.cnt=Integer.parseInt("");
            }
            catch(Exception e2){}
        }
        if(b==close)
        {
            int opn=0;
            String []opt={"NO","YES"};
            while(true)
            {
                opn=JOptionPane.showOptionDialog(null,"Are you sure you want to quit the MODIFY ENTRY FORM?","Confirmation",JOptionPane.NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,opt,0);
                if(opn==0)
                    break;
                else
                {
                    m.setVisible(false);
                    break;
                }
            }
        }
        if(b==rfrsh)
        {
            m.setVisible(false);
            modForm mf=new modForm("MODIFY ENTRY");
        }
        tnm.setText("");
        tid.setText("");
        cdt.select("DD");
        cmnth.select("MM");
        cyr.select("YYYY");
        cblg.select("Blood Group");
        ml.setState(true);
        tage.setText("");
        twt.setText("");
        tcno.setText("");
        temail.setText("");
        tcnt.setText("");
        cdom.select("Domain Name");
        tnm.requestFocus();
    }

    public void itemStateChanged(ItemEvent n){}

    public void focusGained(FocusEvent e)
    {
        JTextField t=(JTextField)e.getSource();
        if(t==tnm)
        {
            tid.setText(""+modify.did);
            tnm.setText(modify.name);
            cblg.select(""+modify.blg);
            tage.setText(""+modify.age);
            twt.setText(""+modify.wt);
            tcno.setText(modify.cno);

            String s1=modify.email;
            String s2="",s3="";
            char ch[]=null;
            int l=s1.length();
            for(int i=1;i<l;i++)
            {
                if(s1.charAt(i)=='@')
                {
                    s3=s1.substring(i,l);
                    s2=s1.substring(0,i);
                    break;
                }
            }
            temail.setText(s2);
            cdom.select(s3);

            cadmyr.select(""+modify.admyr);
            if(modify.gen.equals("Male"))
                gn.setSelectedCheckbox(ml);
            else
                gn.setSelectedCheckbox(fl);
            tcnt.setText(""+modify.cnt);
            cdt.select(""+modify.dt);
            cmnth.select(""+modify.mnth);
            cyr.select(""+modify.yr);
        }
    }

    public void focusLost(FocusEvent e)
    {}
}

class modForm extends JFrame
{
    JTabbedPane tb;
    
    modForm(String s)
    {
        super(s);
        tb=new JTabbedPane();
        tb.addTab("       SELECT ENTRY          ",new panel1(this));
        tb.addTab("       MODIFY ENTRY          ",new panel2(this));
        add(tb);
        setSize(1000,700);
        setVisible(true);
    }
}

class delete
{
    static int did;
    static String name;
    static String dt;
    static PreparedStatement dl,dl1,ds,ds1,uc,sd,ud,ubb,snm;
    static ResultSet rs;
    static Connection con;

    static
    {
        did=0;
        dt="";
        rs=null;
        try
        {
            Class.forName("org.postgresql.Driver");
            con=DriverManager.getConnection("jdbc:postgresql:postgres","postgres","it");
            ds=con.prepareStatement("select * from donor");
            dl=con.prepareStatement("delete from donor where did=?");
            dl1=con.prepareStatement("delete from bbinfo where did=?");
            ds1=con.prepareStatement("select * from culdesac where dt=?");
            uc=con.prepareStatement("update culdesac set count=count-1 where dt=?");
            sd=con.prepareStatement("select * from donor where did=?");
            ud=con.prepareStatement("update donor set did=did-1 where did=?");
            ubb=con.prepareStatement("update bbinfo set did=did-1 where did=?");
            snm=con.prepareStatement("select * from donor where name=?");
        }
        catch(Exception e1){}
    }

    static ResultSet disp() throws Exception
    {
        rs=ds.executeQuery();
        return rs;
    }

    static void del() throws Exception
    {
        dl.setInt(1,did);
        dl1.setInt(1,did);
        dl.executeUpdate();
        dl1.executeUpdate();
        for(did=did+1;searchDon();did++)
        {
            updtDon();
        }
    }

    static boolean searchCul() throws Exception
    {
        ds1.setString(1,dt);
        rs=ds1.executeQuery();
        return rs.next();
    }

    static void upCul() throws Exception
    {
        uc.setString(1,dt);
        uc.executeUpdate();
    }

    static boolean searchDon() throws Exception
    {
        sd.setInt(1,did);
        rs=sd.executeQuery();
        return rs.next();
    }

    static void updtDon() throws Exception
    {
        ud.setInt(1,did);
        ud.executeUpdate();
        ubb.setInt(1,did);
        ubb.executeUpdate();
    }

    static ResultSet sName() throws Exception
    {
        snm.setString(1,name);
        rs=snm.executeQuery();
        return rs;
    }

}

class delForm extends JFrame implements ActionListener,FocusListener
{
    DefaultTableCellRenderer renderer;
    JSplitPane jsp;
    JTable jt;
    JScrollPane js;
    Dimension d,d1;
    JPanel p,q;
    ResultSet k;
    JLabel did,name,dt;
    JTextField tdid,tnm,tdt;
    JButton del,close,refresh;
    String a,b,c;

    delForm(String s)
    {
        super(s);
        renderer=new DefaultTableCellRenderer();
        jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        table();

        p=new JPanel();
        q=new JPanel();
        did=new JLabel("Donor ID :-");
        did.setBounds(76,485,60,30);
        name=new JLabel("Name :-");
        name.setBounds(89,445,60,30);
        dt=new JLabel("Date :-");
        dt.setBounds(95,525,60,30);
        tdid=new JTextField();
        tdid.setBounds(148,490,60,23);
        tdid.addFocusListener(this);
        tdid.setEditable(false);
        tnm=new JTextField();
        tnm.setBounds(148,450,390,23);
        tnm.addFocusListener(this);
        tnm.setEditable(true);
        tdt=new JTextField();
        tdt.setBounds(148,530,150,23);
        tdt.addFocusListener(this);
        tdt.setEditable(false);
        add(did);add(tdid);
        add(name);add(tnm);
        add(dt);add(tdt);
        del=new JButton("DELETE");
        del.addActionListener(this);
        close=new JButton("CLOSE");
        close.addActionListener(this);
        refresh=new JButton("REFRESH");
        refresh.addActionListener(this);
        q.add(del);
        q.add(close);
        q.add(refresh);
        q.setLayout(new GridLayout(1,2,3,3));
        add(q,BorderLayout.SOUTH);
        add(q,BorderLayout.SOUTH);

        d=new Dimension(600,500);
        d1=new Dimension(600,200);
        jsp.setTopComponent(js);
        js.setMinimumSize(d);
        p.setMinimumSize(d1);
        jsp.setBottomComponent(p);
        add(jsp);
 
        setSize(600,650);
        setVisible(true);
    }

    void table()
    {
        jt=new JTable();
        k=null;
        String [][]arr=new String[300][4];
        String []chd={"ID","Name","BloodGr","Date"};
        js=new JScrollPane(jt);
        int i=0;
        try
        {
            k=delete.disp();
            while(k.next())
            {
                arr[i][0]=""+k.getInt(1);
                arr[i][1]=k.getString(2);
                arr[i][2]=k.getString(3);
                arr[i][3]=""+k.getInt(9)+"/"+k.getInt(10)+"/"+k.getInt(11);
                i++;
            }
        }
        catch(Exception ew){}
        DefaultTableModel model=new DefaultTableModel(arr,chd);
        jt.setModel(model);
        jt.setAutoCreateColumnsFromModel(false);
        sortAllRowsBy(model,0,true);
        setTableCellAlignment(0);
    }

    void setTableCellAlignment(int alignment)
    {
        renderer.setHorizontalAlignment(alignment);
        for(int i=0;i<jt.getColumnCount();i++)
        {
            jt.setDefaultRenderer(jt.getColumnClass(i),renderer);
        }
        // repaint to show table cell changes
        jt.updateUI();
     }

    public void sortAllRowsBy(DefaultTableModel model, int colIndex, boolean ascending)
    {
        Vector data = model.getDataVector();
        Collections.sort(data, new ColumnSorter(colIndex, ascending));
        model.fireTableStructureChanged();
    }
    
    public void actionPerformed(ActionEvent e)
    {
        JButton b=(JButton)e.getSource();
        boolean b1=false;
        if(b==del)
        {
            try
            {
                delete.dt=tdt.getText();
                delete.did=Integer.parseInt(tdid.getText());
                delete.del();
                b1=delete.searchCul();
                if(b1)
                {
                    delete.upCul();
                }
            }
            catch(Exception ew){}
            delForm df=new delForm("DELETE ENTRY");
            setVisible(false);
        }
        if(b==close)
        {
            int opn=0;
            String []opt={"NO","YES"};
            while(true)
            {
                opn=JOptionPane.showOptionDialog(null,"Are you sure you want to quit the DELETE ENTRY FORM?","Confirmation",JOptionPane.NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,opt,0);
                if(opn==0)
                    break;
                else
                {
                    setVisible(false);
                    break;
                }
            }
        }
        if(b==refresh)
        {
            setVisible(false);
            delForm dl=new delForm("DELETE ENTRY");
        }
        tdid.setText("");
        tnm.setText("");
        tdt.setText("");
    }

    public void focusGained(FocusEvent e)
    {
        JTextField t=(JTextField)e.getSource();
        try
        {
            a=""+jt.getModel().getValueAt(jt.getSelectedRow(),0);
            b=""+jt.getModel().getValueAt(jt.getSelectedRow(),1);
            c=""+jt.getModel().getValueAt(jt.getSelectedRow(),3);
        }
        catch(Exception ew)
        {
            a="";
            b="";
            c="";
        }
        if(t==tnm)
        {
            if(tnm.getText().equals(""))
            {
                tdid.setText(a);
                tnm.setText(b);
                tdt.setText(c);
            }
        }
        if(t==tdid)
        {
            if(tnm.getText().equals(""))
            {
                tdid.setText(a);
                tnm.setText(b);
                tdt.setText(c);
            }
        }
        if(t==tdt)
        {
            if(tnm.getText().equals(""))
            {
                tdid.setText(a);
                tnm.setText(b);
                tdt.setText(c);
            }
        }
    }

    public void focusLost(FocusEvent e)
    {
        JTextField t=(JTextField)e.getSource();
        if(t==tnm)
        {
            if(tdid.getText().equals("")||tdt.getText().equals(""))
            {
                try
                {
                    delete.name=tnm.getText();
                    k=delete.sName();
                    if(k.next())
                    {
                        tdid.setText(""+k.getInt(1));
                        tdt.setText(""+k.getInt(9)+"/"+k.getInt(10)+"/"+k.getInt(11));
                        JOptionPane.showMessageDialog(null,""+k.getInt(11)+"/"+k.getInt(12)+"/"+k.getInt(13));
                    }
                }
                catch(Exception ew){}
            }
        }
    }
}

// Classes for Search

class search
{
    static int m1,m2,m3,y1,y2,y3,ady1,ady2;
    static String blg,bb;
    static PreparedStatement ds,bs1,bs2,bbs,bbnm,scmp;
    static ResultSet rs;
    static Connection con;

    static
    {
        m1=m2=m3=y1=y2=y3=ady1=ady2=0;
        blg=bb="";
        rs=null;
        try
        {
            Class.forName("org.postgresql.Driver");
            con=DriverManager.getConnection("jdbc:postgresql:postgres","postgres","it");
            ds=con.prepareStatement("select * from donor");
            bs1=con.prepareStatement("select * from donor where blg=? and wt>=60 and ((mnth not in (?)) and (yr not in (?))) and ((mnth not in (?)) and (yr not in (?))) and ((mnth not in (?)) and (yr not in (?))) and (admyr between ? and ?)");
            bs2=con.prepareStatement("select * from donor where blg=? and wt<60 and ((mnth not in (?)) and (yr not in (?))) and ((mnth not in (?)) and (yr not in (?))) and ((mnth not in (?)) and (yr not in (?))) and (admyr between ? and ?)");
            bbs=con.prepareStatement("select distinct(bb) from bbinfo");
            bbnm=con.prepareStatement("select d.name, d.wt, d.age, b.bb, d.blg from donor d, bbinfo b where d.did=b.did and b.bb=? and d.blg=? and d.admyr between ? and ?");
            scmp=con.prepareStatement("select * from culdesac");
        }
        catch(Exception e1){}
    }

    static ResultSet disp() throws Exception
    {
        rs=ds.executeQuery();
        return rs;
    }

    static ResultSet disp1() throws Exception
    {
        bs1.setString(1,blg);
        bs1.setInt(2,m1);
        bs1.setInt(3,y1);
        bs1.setInt(4,m2);
        bs1.setInt(5,y2);
        bs1.setInt(6,m3);
        bs1.setInt(7,y3);
        bs1.setInt(8,ady1);
        bs1.setInt(9,ady2);
        rs=bs1.executeQuery();
        return rs;
    }

    static ResultSet disp2() throws Exception
    {
        bs2.setString(1,blg);
        bs2.setInt(2,m1);
        bs2.setInt(3,y1);
        bs2.setInt(4,m2);
        bs2.setInt(5,y2);
        bs2.setInt(6,m3);
        bs2.setInt(7,y3);
        bs2.setInt(8,ady1);
        bs2.setInt(9,ady2);
        rs=bs2.executeQuery();
        return rs;
    }

    static ResultSet searchBB() throws Exception
    {
        rs=bbs.executeQuery();
        return rs;
    }

    static ResultSet searchBBNM() throws Exception
    {
        bbnm.setString(1,bb);
        bbnm.setString(2,blg);
        bbnm.setInt(3,ady1);
        bbnm.setInt(4,ady2);
        rs=bbnm.executeQuery();
        return rs;
    }

    static ResultSet sCamp() throws Exception
    {
        rs=scmp.executeQuery();
        return rs;
    }
}

class srcForm extends JFrame
{
    JTabbedPane tb;

    srcForm(String s)
    {
        super(s);
        tb=new JTabbedPane();
        tb.addTab("          DONORS RECORDS               ",new panel11(this));
        tb.addTab("          BLOOD  GROUP  RECORDS        ",new panel12(this));
        tb.addTab("          BLOOD  BANK  RECORDS         ",new panel13(this));
        tb.addTab("          CAMP RECORDS                 ",new panel14(this));
        add(tb);
        setSize(1000,700);
        setVisible(true);
    }
}

class panel11 extends JPanel implements ActionListener
{
    DefaultTableCellRenderer renderer;
    srcForm s;
    ResultSet k;
    JTable jt;
    JScrollPane js;
    JButton close;

    panel11(srcForm s1)
    {
        setLayout(null);
        s=s1;
        renderer=new DefaultTableCellRenderer();
        jt=new JTable();
        k=null;
        String [][]arr=new String[5000][11];
        String []chd={"ID","Name","BloodGr","Date","Age","Weight","Gender","Frequency","Adm. Year","Contact No","E-mail"};
        js=new JScrollPane(jt);
        js.setBounds(0,0,970,600);
        int i=0;
        try
        {
            k=search.disp();
            while(k.next())
            {
                arr[i][0]=""+k.getInt(1);
                arr[i][1]=k.getString(2);
                arr[i][2]=k.getString(3);
                arr[i][3]=""+k.getInt(9)+"/"+k.getInt(10)+"/"+k.getInt(11);
                arr[i][4]=""+k.getInt(4);
                arr[i][5]=""+k.getInt(5);
                arr[i][6]=k.getString(8);
                arr[i][7]=""+k.getInt(13);
                arr[i][8]=""+k.getInt(12);
                arr[i][9]=k.getString(6);
                arr[i][10]=k.getString(7);
                i++;
            }
        }
        catch(Exception ew){}
        DefaultTableModel model=new DefaultTableModel(arr,chd);
        jt.setModel(model);
        setTableCellAlignment(0);
        jt.setAutoCreateColumnsFromModel(false);
        sortAllRowsBy(model,0,true);

        close=new JButton("Close");
        close.addActionListener(this);
        close.setBounds(400,603,200,31);
        add(js);
        add(close);
    }

    public void actionPerformed(ActionEvent e)
    {
        String []opt={"NO","YES"};
        int opn=0;
        JButton b=(JButton)e.getSource();
        if(b==close)
        {
            while(true)
            {
                opn=JOptionPane.showOptionDialog(null,"Are you sure you want to quit the SEARCH Form and return to Main Menu?","Confirmation",JOptionPane.NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,opt,0);
                if(opn==0)
                    break;
                else
                {
                    s.setVisible(false);
                    break;
                }
            }
        }
    }

    void setTableCellAlignment(int alignment)
    {
        renderer.setHorizontalAlignment(alignment);
        for(int i=0;i<jt.getColumnCount();i++)
        {
            jt.setDefaultRenderer(jt.getColumnClass(i),renderer);
        }
        // repaint to show table cell changes
        jt.updateUI();
     }

    public void sortAllRowsBy(DefaultTableModel model, int colIndex, boolean ascending)
    {
        Vector data = model.getDataVector();
        Collections.sort(data, new ColumnSorter(colIndex, ascending));
        model.fireTableStructureChanged();
    }
}

class panel12 extends JPanel implements ActionListener
{
    srcForm s;
    JLabel blg,age;
    Choice cblg;
    CheckboxGroup gn;
    Checkbox ml,fl;
    DefaultTableCellRenderer renderer;
    JSplitPane jsp;
    ResultSet k,rs1;
    JTable jt;
    JScrollPane js;
    Dimension d,d1;
    JPanel pp;
    JButton ok,close;
    String [][]arr;
    
    panel12(srcForm s2)
    {
        s=s2;
        renderer=new DefaultTableCellRenderer();
        setLayout(null);
        jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        jt=new JTable();
        k=null;
        arr=new String[300][7];
        String []chd={"Name","BloodGr","Gender","Admission Year","Frequency","Contact No","E-mail"};
        js=new JScrollPane(jt);
        js.setBounds(0,0,970,600);
        int i=0;
        try
        {
            while(i<300)
            {
                arr[i][0]="";
                arr[i][1]="";
                arr[i][2]="";
                arr[i][3]="";
                arr[i][4]="";
                arr[i][5]="";
                arr[i][6]="";
                i++;
             }
        }
        catch(Exception ew){}
        DefaultTableModel model=new DefaultTableModel(arr,chd);
        jt.setModel(model);
        setTableCellAlignment(0);
        jt.setAutoCreateColumnsFromModel(false);
        sortAllRowsBy(model,0,true);

        pp=new JPanel();
        pp.setLayout(null);
        blg=new JLabel("Blood Group :-");
        blg.setBounds(355,45,90,30);
        age=new JLabel("Weight :-");
        age.setBounds(355,105,90,30);
        cblg=new Choice();
        cblg.add("Select Blood Group");
        cblg.add("A+");
        cblg.add("A-");
        cblg.add("B+");
        cblg.add("B-");
        cblg.add("AB+");
        cblg.add("AB-");
        cblg.add("O+");
        cblg.add("O-");
        cblg.setBounds(442,53,140,23);
        // Checkbox
        gn=new CheckboxGroup();
        ml=new Checkbox("Below 60",true,gn);
        ml.setBounds(417,101,80,40);
        fl=new Checkbox("Above 60",true,gn);
        fl.setBounds(497,101,80,40);
        ok=new JButton("OK");
        ok.addActionListener(this);
        ok.setBounds(305,166,160,30);
        close=new JButton("Close");
        close.addActionListener(this);
        close.setBounds(480,166,160,30);
        pp.add(ok);
        pp.add(close);

        pp.add(blg); pp.add(cblg);
        pp.add(age); pp.add(ml); pp.add(fl);

        d=new Dimension(600,500);
        d1=new Dimension(600,200);
        jsp.setTopComponent(pp);
        js.setMaximumSize(d);
        pp.setMinimumSize(d1);
        jsp.setBottomComponent(js);
        jsp.setBounds(0,0,970,635);
        add(jsp);
    }

    public void actionPerformed(ActionEvent e)
    {
        String []opt={"NO","YES"};
        int opn=0;
        JButton b=(JButton)e.getSource();
        Checkbox g=gn.getSelectedCheckbox();
        Calendar cal=Calendar.getInstance();

        if(b==ok)
        {
            search.blg=cblg.getSelectedItem();
            int p=0,q=0,r=0;
            int j=0,k=0,l=0;
            p=cal.get(Calendar.MONTH);
            j=cal.get(Calendar.YEAR);
            if(p==2)
            {
                q=1;
                r=12;
                k=j;
                l=j-1;
            }
            if(p==1)
            {
                q=12;
                r=11;
                k=j-1;
                l=j-1;
            }
            else
            {
                p=cal.get(Calendar.MONTH);
                p=p+1;
                q=p-1;
                r=p-2;
                k=j;
                l=j;
            }
            search.m1=1;
            search.m2=2;
            search.m3=3;
            search.y1=2011;
            search.y2=2011;
            search.y3=2011;
            search.ady1=(cal.get(Calendar.YEAR)-4);
            search.ady2=cal.get(Calendar.YEAR);

            if(g.getLabel().equals("Above 60"))
            {
                arr=new String[300][7];
                String []chd={"Name","BloodGr","Gender","Admission Year","Frequency","Contact No","E-mail"};
                js=new JScrollPane(jt);
                js.setBounds(0,0,970,600);
                rs1=null;
                int i=0;
                try
                {
                    rs1=search.disp1();
                    while(rs1.next())
                    {
                        arr[i][0]=rs1.getString(2);
                        arr[i][1]=rs1.getString(3);
                        arr[i][2]=rs1.getString(8);
                        arr[i][3]=""+rs1.getInt(12);
                        arr[i][4]=""+rs1.getInt(13);
                        arr[i][5]=rs1.getString(6);
                        arr[i][6]=rs1.getString(7);
                        i++;
                    }
                }
                catch(Exception ew){}
                DefaultTableModel model=new DefaultTableModel(arr,chd);
                jt.setModel(model);
                setTableCellAlignment(0);
                jt.setAutoCreateColumnsFromModel(false);
                sortAllRowsBy(model,0,true);

                d=new Dimension(600,500);
                d1=new Dimension(600,200);
                jsp.setTopComponent(pp);
                js.setMaximumSize(d);
                pp.setMinimumSize(d1);
                jsp.setBottomComponent(js);
                jsp.setBounds(0,0,970,635);
                add(jsp);
            }
            else
            {
                arr=new String[300][7];
                String []chd={"Name","BloodGr","Gender","Admission Year","Frequency","Contact No","E-mail"};
                js=new JScrollPane(jt);
                js.setBounds(0,0,970,600);
                int i=0;
                rs1=null;
                try
                {
                    rs1=search.disp2();
                    while(rs1.next())
                    {
                        arr[i][0]=rs1.getString(2);
                        arr[i][1]=rs1.getString(3);
                        arr[i][2]=rs1.getString(8);
                        arr[i][3]=""+rs1.getInt(12);
                        arr[i][4]=""+rs1.getInt(13);
                        arr[i][5]=rs1.getString(6);
                        arr[i][6]=rs1.getString(7);
                        i++;
                    }
                }
                catch(Exception ew)
                {}
                DefaultTableModel model=new DefaultTableModel(arr,chd);
                jt.setModel(model);
                setTableCellAlignment(0);
                jt.setAutoCreateColumnsFromModel(false);
                sortAllRowsBy(model,0,true);

                d=new Dimension(600,500);
                d1=new Dimension(600,200);
                jsp.setTopComponent(pp);
                js.setMaximumSize(d);
                pp.setMinimumSize(d1);
                jsp.setBottomComponent(js);
                jsp.setBounds(0,0,970,635);
            }
        }
        if(b==close)
        {
            while(true)
            {
                opn=JOptionPane.showOptionDialog(null,"Are you sure you want to quit the SEARCH Form and return to Main Menu?","Confirmation",JOptionPane.NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,opt,0);
                if(opn==0)
                    break;
                else
                {
                    s.setVisible(false);
                    break;
                }
            }
        }
    }
   
    void setTableCellAlignment(int alignment)
    {
        renderer.setHorizontalAlignment(alignment);
        for(int i=0;i<jt.getColumnCount();i++)
        {
            jt.setDefaultRenderer(jt.getColumnClass(i),renderer);
        }
        // repaint to show table cell changes
        jt.updateUI();
    }

    public void sortAllRowsBy(DefaultTableModel model, int colIndex, boolean ascending)
    {
        Vector data = model.getDataVector();
        Collections.sort(data, new ColumnSorter(colIndex, ascending));
        model.fireTableStructureChanged();
    }

}

class panel13 extends JPanel implements ActionListener
{
    srcForm s;
    JLabel blg,bb;
    Choice cblg,cbb;
    DefaultTableCellRenderer renderer;
    JSplitPane jsp;
    ResultSet k,rs1;
    JTable jt;
    JScrollPane js;
    Dimension d,d1;
    JPanel pp;
    JButton ok,close;
    String [][]arr;

    panel13(srcForm s3)
    {
        s=s3;
        renderer=new DefaultTableCellRenderer();
        setLayout(null);
        jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        jt=new JTable();
        k=null;
        arr=new String[300][5];
        String []chd={"Name","Weight","Age","Blood Bank","BloodGr"};
        js=new JScrollPane(jt);
        js.setBounds(0,0,970,600);
        int i=0;
        try
        {
            while(i<300)
            {
                arr[i][0]="";
                arr[i][1]="";
                arr[i][2]="";
                arr[i][3]="";
                arr[i][4]="";
                i++;
             }
        }
        catch(Exception ew){}
        DefaultTableModel model=new DefaultTableModel(arr,chd);
        jt.setModel(model);
        setTableCellAlignment(0);
        jt.setAutoCreateColumnsFromModel(false);
        sortAllRowsBy(model,0,true);

        pp=new JPanel();
        pp.setLayout(null);
        blg=new JLabel("Blood Group :-");
        blg.setBounds(355,45,90,30);
        bb=new JLabel("Blood Bank :-");
        bb.setBounds(355,105,90,30);
        cblg=new Choice();
        cblg.add("Select Blood Group");
        cblg.add("A+");
        cblg.add("A-");
        cblg.add("B+");
        cblg.add("B-");
        cblg.add("AB+");
        cblg.add("AB-");
        cblg.add("O+");
        cblg.add("O-");
        cblg.setBounds(442,53,140,23);
        cbb=new Choice();
        cbb.add("Select Blood Bank");
        cbb.setBounds(442,110,140,30);
        try
        {
            k=search.searchBB();
            while(k.next())
            {
                cbb.add(k.getString(1));
            }
        }
        catch(Exception we)
        {}
        ok=new JButton("OK");
        ok.addActionListener(this);
        ok.setBounds(305,166,160,30);
        close=new JButton("Close");
        close.addActionListener(this);
        close.setBounds(480,166,160,30);
        pp.add(ok);
        pp.add(close);

        pp.add(blg); pp.add(cblg);
        pp.add(bb); pp.add(cbb);

        d=new Dimension(600,500);
        d1=new Dimension(600,200);
        jsp.setTopComponent(pp);
        js.setMaximumSize(d);
        pp.setMinimumSize(d1);
        jsp.setBottomComponent(js);
        jsp.setBounds(0,0,970,635);
        add(jsp);
    }

    public void actionPerformed(ActionEvent e)
    {
        String []opt={"NO","YES"};
        int opn=0;
        JButton b=(JButton)e.getSource();
        Calendar cal=Calendar.getInstance();
        if(b==ok)
        {
            search.blg=cblg.getSelectedItem();
            search.bb=cbb.getSelectedItem();
            search.ady1=(cal.get(Calendar.YEAR)-4);
            search.ady2=cal.get(Calendar.YEAR);
            arr=new String[5000][5];
            String []chd={"Name","Weight","Age","Blood Bank","BloodGr"};
            js=new JScrollPane(jt);
            js.setBounds(0,0,970,600);
            int i=0;
            rs1=null;
            try
            {
                rs1=search.searchBBNM();
                while(rs1.next())
                {
                    arr[i][0]=rs1.getString(1);
                    arr[i][1]=""+rs1.getInt(2);
                    arr[i][2]=""+rs1.getInt(3);
                    arr[i][3]=rs1.getString(4);
                    arr[i][4]=rs1.getString(5);
                    i++;
                 }
             }
             catch(Exception ew)
             {}
             DefaultTableModel model=new DefaultTableModel(arr,chd);
             jt.setModel(model);
             setTableCellAlignment(0);
             jt.setAutoCreateColumnsFromModel(false);
             sortAllRowsBy(model,0,true);

             d=new Dimension(600,500);
             d1=new Dimension(600,200);
             jsp.setTopComponent(pp);
             js.setMaximumSize(d);
             pp.setMinimumSize(d1);
             jsp.setBottomComponent(js);
             jsp.setBounds(0,0,970,635);
        }
        if(b==close)
        {
            while(true)
            {
                opn=JOptionPane.showOptionDialog(null,"Are you sure you want to quit the SEARCH Form and return to Main Menu?","Confirmation",JOptionPane.NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,opt,0);
                if(opn==0)
                    break;
                else
                {
                    s.setVisible(false);
                    break;
                }
            }
        }
    }

    void setTableCellAlignment(int alignment)
    {
        renderer.setHorizontalAlignment(alignment);
        for(int i=0;i<jt.getColumnCount();i++)
        {
            jt.setDefaultRenderer(jt.getColumnClass(i),renderer);
        }
        // repaint to show table cell changes
        jt.updateUI();
    }

    public void sortAllRowsBy(DefaultTableModel model, int colIndex, boolean ascending)
    {
        Vector data = model.getDataVector();
        Collections.sort(data, new ColumnSorter(colIndex, ascending));
        model.fireTableStructureChanged();
    }
}

class panel14 extends JPanel implements ActionListener
{
    srcForm s;
    DefaultTableCellRenderer renderer;
    ResultSet k;
    JTable jt;
    JScrollPane js;
    JButton close;
    panel14(srcForm s4)
    {
        s=s4;
        setLayout(null);
        renderer=new DefaultTableCellRenderer();
        jt=new JTable();
        k=null;
        String [][]arr=new String[500][7];
        String []chd={"Date","Count","BloodBank 1","BloodBank 2","BloodBank 3","BloodBank 4","BloodBank 5"};
        js=new JScrollPane(jt);
        js.setBounds(0,0,970,600);
        int i=0;
        try
        {
            k=search.sCamp();
            while(k.next())
            {
                arr[i][0]=k.getString(1);
                arr[i][1]=""+k.getInt(2);
                arr[i][2]=k.getString(3);
                arr[i][3]=k.getString(4);
                arr[i][4]=k.getString(5);
                arr[i][5]=k.getString(6);
                arr[i][6]=k.getString(7);
                i++;
            }
        }
        catch(Exception ew){}
        DefaultTableModel model=new DefaultTableModel(arr,chd);
        jt.setModel(model);
        setTableCellAlignment(0);
        jt.setAutoCreateColumnsFromModel(false);
        sortAllRowsBy(model,0,true);

        close=new JButton("Close");
        close.addActionListener(this);
        close.setBounds(400,603,200,31);
        add(js);
        add(close);
    }

    public void actionPerformed(ActionEvent e)
    {
        String []opt={"NO","YES"};
        int opn=0;
        JButton b=(JButton)e.getSource();
        if(b==close)
        {
            while(true)
            {
                opn=JOptionPane.showOptionDialog(null,"Are you sure you want to quit the SEARCH Form and return to Main Menu?","Confirmation",JOptionPane.NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,opt,0);
                if(opn==0)
                    break;
                else
                {
                    s.setVisible(false);
                    break;
                }
            }
        }
    }

    void setTableCellAlignment(int alignment)
    {
        renderer.setHorizontalAlignment(alignment);
        for(int i=0;i<jt.getColumnCount();i++)
        {
            jt.setDefaultRenderer(jt.getColumnClass(i),renderer);
        }
        // repaint to show table cell changes
        jt.updateUI();
     }

    public void sortAllRowsBy(DefaultTableModel model, int colIndex, boolean ascending)
    {
        Vector data = model.getDataVector();
        Collections.sort(data, new ColumnSorter(colIndex, ascending));
        model.fireTableStructureChanged();
    }
}

class LoginForm extends JFrame implements ActionListener
{
    JLabel usr,pass;
    JPasswordField pwd;
    JTextField tusr;
    JButton ok,can,chng;
    JPanel p;
    LoginForm(String s)
    {
        super(s);
        p=new JPanel();
        usr=new JLabel("User Name:");
        pass=new JLabel("Password:");
        tusr=new JTextField();
        tusr.setBounds(400,400,50,60);
        ok=new JButton("OK");
        ok.addActionListener(this);
        can=new JButton("CANCLE");
        can.addActionListener(this);
        chng=new JButton("MODIFY");
        chng.addActionListener(this);
        p.setLayout(null);
        p.setBounds(0,0,1350,50);
        p.add(ok);
        p.add(can);
        p.add(chng);
        add(tusr);

        setVisible(true);
        setBounds(0,0,1350,800);

    }

    public void actionPerformed(ActionEvent er)
    {
        JButton b=(JButton)er.getSource();
        if(b==ok)
        {
            MainFrame mf=new MainFrame("Welcome");
        }
    }
}
class MainFrame extends JFrame implements ActionListener
{
    JButton ba,bm,bs,bd,bh,bc,bab;
    ImageIcon ii;
    Image i;

    MainFrame(String s)
    {
        super(s);
        JPanel p=new JPanel();
        JPanel q=new JPanel();
        ii=new ImageIcon("G:\\TE-I\\Project\\BloodBankManagement\\dist\\lib\\BD.jpg");
        i=ii.getImage();
        JLabel j=new JLabel()
        {
            public void paint(Graphics g)
            {
                g.drawImage(i,0,0,this);
            }
        };
        j.setBounds(0,100,5,5);
        ba=new JButton("ADD ENTRY");
        ba.addActionListener(this);
        bm=new JButton("MODIFY ENTRY");
        bm.addActionListener(this);
        bs=new JButton("SEARCH");
        bs.addActionListener(this);
        bd=new JButton("DELETE");
        bd.addActionListener(this);
        bab=new JButton("ABOUT");
        bab.addActionListener(this);
        bc=new JButton("CLOSE");
        bc.addActionListener(this);
        bh=new JButton("HELP");
        bh.addActionListener(this);
        p.setLayout(new GridLayout(1,4,5,5));
        p.setBounds(0,0,1350,50);
        p.add(ba);
        p.add(bm);
        p.add(bd);
        p.add(bs);
        q.setLayout(new GridLayout(1,3,20,20));
        q.setBounds(0,650,1367,50);
        q.add(bab);
        q.add(bh);
        q.add(bc);
        add(p);
        add(q);
        JPanel r=new JPanel();
        add(r,BorderLayout.CENTER);
        add(j);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        setSize(1367,737);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        JButton b=(JButton)e.getSource();
        if(b==ba)
        {
            donForm df=new donForm("ENTRY FORM");
        }
        if(b==bm)
        {
            modForm mf=new modForm("MODIFY ENTRY");
        }
        if(b==bd)
        {
            delForm dl=new delForm("DELETE ENTRY");
        }
        if(b==bc)
        {
            int opn=0;
            String []opt={"NO","YES"};
            while(true)
            {
                opn=JOptionPane.showOptionDialog(null,"Are you sure you want to quit the BloodBank?","Confirmation",JOptionPane.NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,opt,0);
                if(opn==0)
                    break;
                else
                {
                    System.exit(0);
                }
            }
        }
        if(b==bs)
        {
            srcForm sf=new srcForm("SEARCH");
        }
        /*if(b==bh)
        {
            help h=new help("Help");
        }
        if(b==ba)
        {
            about a=new about("About");
        }*/
    }
}

public class Main
{
    public static void main(String []args)
    {
        LoginForm lf=new LoginForm("Login");
    }
}