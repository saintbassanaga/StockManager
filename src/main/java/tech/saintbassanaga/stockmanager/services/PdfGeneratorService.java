package tech.saintbassanaga.stockmanager.services;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;
import tech.saintbassanaga.stockmanager.models.Inventory;
import tech.saintbassanaga.stockmanager.models.Product;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by saintbassanaga {saintbassanaga}
 * In the Project StockManager at Mon - 9/16/24
 */
@Service
public class PdfGeneratorService {

    public String generateInventoryPdf(Inventory inventory) throws FileNotFoundException {
        String dest = "inventory_" + inventory.getUuid() + ".pdf";
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // Group products by category
        Map<String, List<Product>> productsByCategory = inventory.getProduct()
                .stream()
                .collect(Collectors.groupingBy(p -> p.getCategory().getDesignation()));

        productsByCategory.forEach((category, products) -> {
            document.add(new Paragraph("Category: " + category));
            for (Product product : products) {
                document.add(new Paragraph("Product: " + product.getName()));
            }
        });

        document.close();
        return dest; // Return the path to the generated PDF
    }
}

