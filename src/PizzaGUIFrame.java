import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;



public class PizzaGUIFrame extends JFrame {

    JPanel mainPnl;
    JPanel btnPnl;

    JRadioButton thin;
    JRadioButton regular;
    JRadioButton deepDish;


    JPanel comboPnl;
    JComboBox<String> comboBox;
    JCheckBox cheese;
    JCheckBox pep;
    JCheckBox sausage;
    JCheckBox bacon;
    JCheckBox pineapple;
    JCheckBox jalapenos;
    JCheckBox bananaPeppers;


    JPanel topPnl;

    JPanel receiptPnl;

    JTextArea receipt;
    JScrollPane scroller;

    JPanel controlPnl;
    JButton orderBtn;
    JButton clearBtn;
    JButton quitBtn;

    JPanel northPnl;

    boolean orderPlaced = false;
        public PizzaGUIFrame() {

            mainPnl = new JPanel();
            mainPnl.setLayout(new BorderLayout());

            northPnl = new JPanel();
            northPnl.setLayout(new BorderLayout());

            createComboPnl();
            northPnl.add(comboPnl,BorderLayout.WEST);

            createCrustPnl();
            northPnl.add(btnPnl,BorderLayout.CENTER);


            createTopPnl();
            northPnl.add(topPnl,BorderLayout.EAST);


            createReceiptPnl();
            mainPnl.add(receiptPnl,BorderLayout.CENTER);

            createControlPnl();
            mainPnl.add(controlPnl,BorderLayout.SOUTH);

            mainPnl.add(northPnl,BorderLayout.NORTH);

            add(mainPnl);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
            setTitle("Jonathon's Pizza Order Form");

            Toolkit kit = Toolkit.getDefaultToolkit();
            Dimension screenSize = kit.getScreenSize();
            int screenHeight = screenSize.height;
            int screenWidth = screenSize.width;
            setSize(3*(screenWidth / 4), 3*(screenHeight / 4));
            setLocationRelativeTo(null);

        }



    private void createCrustPnl() {
        btnPnl = new JPanel();
        btnPnl.setLayout(new GridLayout(0, 1));

        btnPnl.setBorder(new TitledBorder(new EtchedBorder(), "Crust (Choose one)*"));

        thin = new JRadioButton("Thin");
        regular = new JRadioButton("Regular");
        deepDish = new JRadioButton("Deep Dish");

        btnPnl.add(regular);
        btnPnl.add(thin);
        btnPnl.add(deepDish);

        regular.setSelected(true);

        ButtonGroup group = new ButtonGroup();
        group.add(thin);
        group.add(regular);
        group.add(deepDish);
    }

    private void createComboPnl() {
        comboPnl = new JPanel();
        comboPnl.setLayout(new GridLayout());
        comboPnl.setBorder(new TitledBorder(new EtchedBorder(), "Size (Choose one)*"));

        comboBox = new JComboBox<>();
        comboBox.addItem("Small  ($8.00)");
        comboBox.addItem("Medium ($12.00)");
        comboBox.addItem("Large  ($16.00)");
        comboBox.addItem("Super  ($20.00)");

        comboPnl.add(comboBox);
    }

    private void createReceiptPnl() {
        receiptPnl = new JPanel();
        receiptPnl.setLayout(new BorderLayout());
        receiptPnl.setBorder(new TitledBorder(new EtchedBorder(), "Order Details"));

        receipt = new JTextArea(20,30);
        scroller = new JScrollPane(receipt);
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        receipt.setEditable(false);
        receiptPnl.add(scroller);
    }

    private void createTopPnl() {
        topPnl = new JPanel();
        topPnl.setLayout(new GridLayout(2, 4));
        topPnl.setBorder(new TitledBorder(new EtchedBorder(), "Toppings ($1 Per Topping)"));

        cheese = new JCheckBox("Extra Cheese");
        pep = new JCheckBox("Pepperoni");
        bacon = new JCheckBox("Bacon");
        sausage = new JCheckBox("Sausage");
        jalapenos = new JCheckBox("Jalapenos");
        bananaPeppers = new JCheckBox("Banana Peppers");
        pineapple = new JCheckBox("Pineapple");

        topPnl.add(cheese);
        topPnl.add(pep);
        topPnl.add(bacon);
        topPnl.add(sausage);
        topPnl.add(jalapenos);
        topPnl.add(pineapple);
        topPnl.add(bananaPeppers);
    }

    private void createControlPnl() {
        controlPnl = new JPanel();
        controlPnl.setLayout(new GridLayout(1, 3));
        orderBtn = new JButton("Place Order");
        orderBtn.addActionListener((ActionEvent ae) -> placeOrder());

        clearBtn = new JButton("Clear");
        clearBtn.addActionListener((ActionEvent ae) -> {
            cheese.setSelected(false);
            pep.setSelected(false);
            sausage.setSelected(false);
            bacon.setSelected(false);
            jalapenos.setSelected(false);
            bananaPeppers.setSelected(false);
            pineapple.setSelected(false);
            receipt.setText(null);
            comboBox.setSelectedIndex(0);
            regular.setSelected(true);
            orderPlaced = false;
        });
        quitBtn = new JButton("Quit");
        quitBtn.addActionListener((ActionEvent ae) -> {
            int choice = JOptionPane.showConfirmDialog(quitBtn, "Are you sure you want to quit?", "Quit",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);}});

        controlPnl.add(orderBtn);
        controlPnl.add(clearBtn);
        controlPnl.add(quitBtn);
    }

    private double subTotal;
    public void placeOrder()
    {

        if(!orderPlaced)
        {
            subTotal = 0;
            addSeparators();
            checkCrustAndSize();
            checkToppings();
            receipt.append("\n\n\n");
            calculatePrice();
            addSeparators();
            orderPlaced = true;
        }
    }



    public void addSeparators()
    {
        receipt.append("================================================================================ \n");
    }

    public void calculatePrice()
    {
        double tax = subTotal * .07;
        double totalPrice = subTotal + tax;


        receipt.append("Sub Total:      $" + String.format("%.2f",subTotal) + "\n");
        receipt.append("Tax:                 $" + String.format("%.2f",tax) +"\n");
        receipt.append("-------------------------------------------------------------------------------------------------------------------------------------------- \n");

        receipt.append( "Total Price:    $" + String.format("%.2f",totalPrice) + "\n");
    }

    public void checkToppings()
    {
        ArrayList<JCheckBox> toppingButtons= new ArrayList<>(Arrays.asList(pep, sausage, bacon, cheese, pineapple, jalapenos, bananaPeppers));
        ArrayList<String> selectedToppings = new ArrayList<>();
        for(int i = 0; i < 7; i++)
        {
            if(toppingButtons.get(i).isSelected())
            {
                selectedToppings.add(toppingButtons.get(i).getText());
            }

        }
        receipt.append("Toppings:  ");
        for(int l = 0 ; l < selectedToppings.size() ; l++)
        {
            subTotal += 1.0;
            if(l < selectedToppings.size() - 1)
                receipt.append(selectedToppings.get(l) + ", ");
            else
                receipt.append(selectedToppings.get(l));
        }
        receipt.append("    $" + selectedToppings.size() + ".00 \n");

    }



    public void checkCrustAndSize()
    {
        String chosenCrust = "";
        if(thin.isSelected())
        {
            chosenCrust = "Thin";
        }
        if(regular.isSelected())
        {
            chosenCrust = "Regular";
        }
        if(deepDish.isSelected())
        {
            chosenCrust = "Deep-Dish";
        }

        String chosenSize = Objects.requireNonNull(comboBox.getSelectedItem()).toString();
        if(Objects.equals(chosenSize, "Small  ($8.00)")){
            receipt.append("Size and Crust Type :   Small, "+ chosenCrust + "     $8.00 \n");
            subTotal += 8.0;
        }
        if(Objects.equals(chosenSize, "Medium ($12.00)"))
        {
            receipt.append("Size and Crust Type :   Medium, "+ chosenCrust + "     $12.00 \n");
            subTotal += 12.0;
        }
        if(Objects.equals(chosenSize, "Large  ($16.00)"))
        {
            receipt.append("Size and Crust Type :   Large, " + chosenCrust + "     $16.00 \n");
            subTotal += 16.0;
        }
        if(Objects.equals(chosenSize, "Super  ($20.00)"))
        {
            receipt.append("Size and Crust Type :   Super, " + chosenCrust + "     $20.00 \n");
            subTotal += 20.0;
        }
    }
}
