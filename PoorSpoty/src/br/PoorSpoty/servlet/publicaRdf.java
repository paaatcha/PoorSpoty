package br.PoorSpoty.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.PoorSpoty.domain.Banda;
import br.PoorSpoty.domain.EstiloMusical;
import br.PoorSpoty.domain.Usuario;
import br.PoorSpoty.persistence.UsuarioDAO;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * Servlet implementation class publicaRdf
 */
@WebServlet("/data/usuariosRDF")
public class publicaRdf extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@EJB
	private UsuarioDAO usuarioDAO;
	
	private static final DateFormat DF = new SimpleDateFormat ("dd-MM-yyyy");
	
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Setando que a saida será em xml
		response.setContentType("text/xml");
		
		// Listando todos os usuarios
		List <Usuario> usuarios = usuarioDAO.listar();
		
		
		// Começando a manipular os modelos do jena
		Model model = ModelFactory.createDefaultModel();
		
		String myNS = "http://localhost:8080/PoorSpoty/data/usuariosRDF";
		String favM = "http://www.pachecoandre.com.br/onto/favMusic#";		
		model.setNsPrefix ("favM",favM);
		
		// Recursos
		Resource favMPessoa = ResourceFactory.createResource(favM + "Pessoa");
		Resource favMBanda = ResourceFactory.createResource(favM + "Banda");
		Resource favMEstiloMusical = ResourceFactory.createResource(favM + "EstiloMusical");
					
		// Propriedades
		Property curte = ResourceFactory.createProperty(favM + "curte");
		Property naoCurte = ResourceFactory.createProperty(favM + "naoCurte");
		Property temUmEstilo = ResourceFactory.createProperty(favM + "temUmEstilo");
		
		Property nome = ResourceFactory.createProperty(favM + "nome");
		Property nick = ResourceFactory.createProperty(favM + "nick");
		Property sobrenome = ResourceFactory.createProperty(favM + "sobrenome");
		Property dataNasc = ResourceFactory.createProperty(favM + "dataNasc");
		Property sobre = ResourceFactory.createProperty(favM + "sobre");
		
		
		for (Usuario user : usuarios){
			if (user.getTipo() == 0) {
				// criando o recurso usuario
				Resource usuarioRDF = model.createResource (myNS + "_" + user.getId());
				usuarioRDF.addProperty(RDF.type, favMPessoa);				
				usuarioRDF.addLiteral(nome,user.getNome());
				usuarioRDF.addLiteral(nick,user.getNick());
				usuarioRDF.addLiteral(sobrenome,user.getSobrenome());
				usuarioRDF.addLiteral(sobre,user.getDescricao());
				usuarioRDF.addLiteral(dataNasc, ResourceFactory.createTypedLiteral(DF.format(user.getDataNasc()),XSDDatatype.XSDdateTime));
				
				List <Banda> bandas = user.getBandas();
				
				for (Banda band : bandas){				
					usuarioRDF.addProperty(curte, model.createResource()
							.addProperty(RDF.type, favMBanda)
							.addLiteral(nome,band.getNome())
							.addLiteral(temUmEstilo,band.getEstilo().getNome())						
						);
				}
				
				List <EstiloMusical> estilosCurte = user.getEstilos();
				
				for (EstiloMusical est : estilosCurte){				
					usuarioRDF.addProperty(curte, model.createResource()
							.addProperty(RDF.type, favMEstiloMusical)
							.addLiteral(nome,est.getNome())													
						);
				}
				
				List <EstiloMusical> estilosNaoCurte = user.getEstilosNao();
				
				for (EstiloMusical estN : estilosNaoCurte){				
					usuarioRDF.addProperty(naoCurte, model.createResource()
							.addProperty(RDF.type, favMEstiloMusical)
							.addLiteral(nome,estN.getNome())													
						);
				}
				
				
			}
		}
		
		
		
		try (PrintWriter out = response.getWriter()){
			model.write(out, "RDF/XML");			
		}
		
	}

}
