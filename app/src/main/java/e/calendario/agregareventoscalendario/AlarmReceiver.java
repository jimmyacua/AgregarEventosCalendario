package e.calendario.agregareventoscalendario;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import java.util.Stack;


public class AlarmReceiver extends BroadcastReceiver {
    private int MID = 0;
    private Stack<String> pilaConsejos;
    private static String consejo;
    NotificationActivity notificationActivity;

    public AlarmReceiver(){
        pilaConsejos = new Stack<>();
        //llenarPilaConsejos();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        llenarPilaConsejos();
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, NotificationActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        consejo = sacarConsejo();

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.ic_new_releases_black_24dp);
        builder.setContentTitle("Consejo del día");
        builder.setContentText(consejo);
        notificationIntent.putExtra("nConsejo", consejo);
        builder.setColor(Color.GREEN);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Notification.DEFAULT_LIGHTS, 1000, 1000);
        builder.setVibrate(new long[]{Notification.DEFAULT_VIBRATE});
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        notificationManager.notify(MID, builder.build());
        MID++;

    }

    public void llenarPilaConsejos(){
        String consejos = "Junta y aplasta las latas de aluminio para reducir su volumen.\n" +
                "Separa las botellas y frascos de vidrio y no los rompas. Los vidrios rotos deben manejarse por separado y con mucho cuidado para evitar accidentes.\n" +
                "Amarra el periódico, las revistas, las hojas de papel y el cartón para facilitar su manejo y traslado. Consérvalos limpios y secos para que puedan reciclarse.\n" +
                "Junta y aplasta todos los envases y botellas de plástico, esto facilita su clasificación y reciclaje.\n" +
                "Bolsas, empaques y envases plástico, vidrio, papel, cartón y metales. Casi todos los residuos inorgánicos se pueden reciclar cuando están libres de materia orgánica.\n" +
                "Done la ropa que ya no utilices con los miembros de tu familia o a una tienda de segunda mano en su localidad, e incluso como obra de caridad.\n" +
                "Dale prioridad a los envases reutilizables para guardar tu comida.\n" +
                "Evita botar las botellas de plástico, puedes encontrar muchas funciones para ellas.\n" +
                "Done todos aquellos juguetes que no se usen.\n" +
                "Haga compras con una cesta o bolsas reutilizables.\n" +
                "Use productos naturales y orgánicos para el mantenimiento de su jardín y evite los fertilizantes que poseen pesticidas.\n" +
                "Compra sólo lo necesario y, si es posible, que sea biodegradable.\n" +
                "Disminuye el consumo de productos desechables o aquellos que contienen varios empaques.\n" +
                "Usa preferentemente tasas, vasos y platos de cerámica.\n" +
                "Utiliza envases retornables.\n" +
                "Lleva bolsa de mandado en lugar de pedir bolsas de plástico.\n" +
                "Usa las hojas de papel por ambos lados.\n" +
                "Elabora cuadernos nuevos con las hojas sobrantes de los cuadernos en desuso.\n" +
                "Organiza ventas de garage o venta de artículos que ya no son útiles.\n" +
                "Utiliza pilas recargables.\n" +
                "Da la máxima utilidad a las cosas sin necesidad de destruirlas.\n" +
                "Usa de nuevo un objeto con otro fin al que se compró.\n" +
                "Separa los residuos en orgánicos e inorgánicos desde el momento en que se generan.\n" +
                "Enjuaga los envases usados de cartón multicapas (jugos, leche, etc.), y aplástalos para ahorrar espacio; los puedes depositar en los contenedores que se encuentran en algunas tiendas de autoservicio.\n" +
                "Localiza centros de acopio que compren materiales reciclables.\n" +
                "Hay que aprovechar bien los artículos antes de deshacerse de ellos.\n" +
                "Utiliza varias veces, hasta que sea posible, todos los objetos que generalmente se van a la basura, o adáptalos como substitutos de otros objetos que puedes utilizar.\n" +
                "Compra presentaciones familiares de productos como yogurt, galletas o cereal en vez de individuales y envía el lunch en empaques reutilizables, con ello generarás menos cantidad de basura.\n" +
                "Toma agua en vasos de cristal, reduce el consumo de botellas de plástico o PET.\n" +
                "Evita productos que tardan miles de años en degradarse como el Unicel, si no puedes evitar utilizar desechables busca los que están hechos con maíz o que tienen materiales biodegradables.\n" +
                "Existen muchos residuos que por sus características son difíciles de reciclar, por lo que terminan casi íntegros dentro de los rellenos sanitarios. Aquí lo recomendable es controlar el consumo de esos productos para reducir su generación. Incluye cosas como: Cerámica, hule, estambre, tela, zapatos, brochas, cosméticos,  juguetes con materiales mezclados, envolturas de frituras y golosinas, espejos, focos, unicel, plumas, plumones, lápices, gomas, discos, casettes, celofán, cepillos de dientes, rastrillos y papel aluminio, entre otros.\n" +
                "A la hora de escoger entre productos semejantes, elige aquel que tenga menos envase y embalaje. Evita el consumo de los productos excesivamente empaquetados, no sólo porque generan más basura sino porque seguramente el sobreprecio de esos empaques lo estarás pagando tú.\n" +
                "Si compras algo pequeño y fácil de cargar no pidas una bolsa de plástico.\n" +
                "Siempre que puedas compra los alimentos producidos lo más cerca posible a tu localidad. Ahorrarás embalajes y transporte.\n" +
                "Reduce tu consumo de papel aluminio y plástico  para envolver, guarda las cosas en contenedores de vidrio preferentemente.\n" +
                "Utiliza vinagre con limón y un poco de aromatizante natural en lugar de productos químicos para limpiar tus pisos y quitar grasa.\n" +
                "Utiliza una naranja o un limón e insértale unos clavos de olor o unos tiestos de albahaca para alejar a los insectos, en lugar de utilizar insecticidas.\n" +
                "Utiliza las latas como macetas o bien para poner lápices o botones.\n" +
                "Coloca separadores de basura adicionales para que clasifiques el vidrio, el papel y el plástico. Si separamos estos productos será más fácil que puedan ser utilizados nuevamente.\n" +
                "Puedes utilizar cajas de cartón, huacales o bolsas de basura y ponerles etiquetas o marcar para identificar cada tipo de residuo.  Notarás que separando de este modo se generará menos basura y no habrá olores fétidos.\n" +
                "Despega, desbarata y aplasta las cajas para que ocupen menos espacio. Quita cualquier material que no sea papel. Envía a reciclar las hojas después de usarlas por ambos lados. Deposítalas sin arrugar o en pedacitos.\n" +
                "Los residuos sanitarios son los que se generan en los baños y es recomendable mantenerlos por separado, no revolverlos ni quemarlos.\n" +
                "Existen otros residuos que contienen materiales tóxicos o que al entrar en contacto con otros generan líquidos contaminantes. Es el caso de las pilas, aparatos electrónicos como celulares, focos ahorradores, cartuchos de toner para impresión, así como aceites lubricantes usados. Actualmente existen muchos centros de empresas privadas como de los gobiernos locales que reciben este tipo de productos en contenedores especiales. Infórmate y llévalos a esos centros especiales para evitar que terminen contaminando los suelos y eventualmente lleguen a nosotros a través de los alimentos que consumimos, el agua que tomamos o el aire que respiramos.\n" +
                "Una excelente forma de reutilizar la basura orgánica, como cáscaras de frutas y verduras, es hacer compost casero que puedas usar como un increíble abono para tus plantas y pasto.\n" +
                "Consume frutas y verduras ecológicas. Los productos ecológicos cuidan el medio ambiente porque en su producción no se utilizan fertilizantes ni otros productos contaminantes.\n" +
                "Cierra los grifos correctamente. Cuando no utilices el agua, cierra el grifo y controla que no existan fugas.\n" +
                "Recicla todo lo que puedas. Antes de tirar ropa, libros o juguetes, piensa si puedes darles una segunda oportunidad para evitar gastar y comprar todo nuevo. Ahorrarás dinero y protegerás la naturaleza.\n" +
                "Planta árboles. Los árboles producen oxígeno y son esenciales para la naturaleza, así que planta un árbol en tu casa o en la comunidad donde vives.\n" +
                "Aprovecha la luz natural. Para reducir el consumo de luz eléctrica, abre las ventanas y sube las persianas para que entre la luz del sol en tu casa.\n" +
                "Antes de tirar cualquier cosa a la basura, piensa si se puede reutilizar, reciclar o reparar, o si puede ser útil para otra persona.\n" +
                "Para ahorrar agua, lo más importante es no desperdiciar. Por eso, cierra el grifo mientras te cepillas los dientes, o mientras te enjabonas en la ducha. Además, es mejor cambiar el baño por la ducha.\n" +
                "Separa los residuos en distintos cubos de basura. Puedes decorar cubos de distintos colores para que sea más fácil identificar qué debes poner en cada bolsa.\n" +
                "Antes de reciclar el papel, recuerda usarlo por las dos caras. Además, puedes comprar papel reciclado, así salvarás árboles.\n" +
                "Usa bombillas de bajo consumo. No sólo ayudarás a ahorrar electricidad, sino que también reducen el gasto en la factura de la luz.\n" +
                "Reutiliza las bolsas de plástico, y utiliza mejor las que sean biodegradables. Guárdalas cuando vayáis a hacer la compra, o usa bolsas de tela.\n" +
                "No abuses de la luz eléctrica, aprovecha la luz natural. Es más saludable ajustar nuestro horario para aprovechar al máximo las horas de luz solar.\n" +
                "Usa cajar, telas, bricks...para hacer manualidades de decoraciones o juguetes. Cualquier objeto se puede reciclar y convertir en algo increíble, ¡usa tu imaginación!\n" +
                "Usa bombillas LED que duran mucho más que las bombillas tradicionales y que inclusiva las fluorescentes.\n" +
                "Evita los aerosoles que contienen CFCs y que son causantes de la destrucción de la capa de ozono, u otros gases que también contribuyen al efecto invernadero.\n" +
                "La gran mayoría de los productos de limpieza que se anuncian no sólo son innecesarios sino también muy nocivos para el medio ambiente.\n" +
                "Los ambientadores no eliminan los malos olores sino que desprenden otros más fuertes que nos impiden detectar los primeros.\n" +
                "Evita usar productos de limpieza agresivos: limpiahornos, lejía, etc., que impiden los procesos biológicos de depuración del agua.\n" +
                "Para ahorrar agua, instala un sistema de doble descarga en el inodoro, ya que vaciar la cisterna entera supone gastar de 10 a litros.\n" +
                "Rechaza los alimentos envasados en bandejas de poliestireno expandido (corcho blanco).\n" +
                "Guarda los alimentos en la clásica fiambrera o tarros de cristal en lugar de envolverlos o taparlos con película de plástico o aluminio.\n" +
                "Si te ha caducado algún medicamento, no lo tires a la basura, llévalo a una farmacia.\n" +
                "No agobies a los niños con juguetes complejos que requieren baterías.\n";
        String [] arrayConsejos = consejos.split("\n");
        for(int i=0; i<67; i++) {
            pilaConsejos.add(arrayConsejos[i]);
        }
    }

    public static String getConsejo() {
        return consejo;
    }

    public String sacarConsejo() {
        int random = (int) (Math.random()*66);
        consejo = pilaConsejos.get(random);
        if(pilaConsejos.empty()){
            llenarPilaConsejos();
        }
        try {
            notificationActivity.setTextConsejo(consejo);
        } catch (Exception e){}
        //notificationActivity.setTextConsejo(consejo);
        return consejo;
    }

}
