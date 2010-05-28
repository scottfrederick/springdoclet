package org.springdoclet

import com.sun.tools.javadoc.Main
import org.junit.Test

class SpringDocletTest {
  @Test
  public void docletInvoked() {
    final String fileName = "spring-doc.html"

    File file = new File("./sample", fileName)
    file.delete()
    assert !file.exists()

    def args = ["-sourcepath", "./sample/src/main/java",
            "-classpath", getClassPath(),
            "-subpackages", "org.springframework.samples.petclinic",
            "-d", "./sample",
            "-f", fileName,
            "-linkpath", "../apidocs/"] as String[]
    println "javadoc args=$args"
    Main.execute "docletTest", SpringDoclet.class.name, args

    assert file.exists()

    def path = new XmlSlurper().parse(file)

    println "Output=[${file.readLines()}]"

    assertPageStructure(path)
    assertComponents(path)
    assertMappings(path)
  }

  def getClassPath() {
    def loaderUrls = this.class.classLoader.URLs
    def files = loaderUrls.collect { new URI(it.toString()).path - '/'}
    return files.join(File.pathSeparator)
  }

  private def assertPageStructure(path) {
    assert 1 == path.head.size()
    assert 1 == path.body.size()
    assert 1 == path.body.h1.size()
    assert 2 == path.body.div.size()
  }

  private def assertComponents(path) {
    def div = path.body.div[0]
    assert 'components' == div.'@id'.toString()
    assert 1 == div.h2.size()
    assert 3 == div.h3.size()
    assert 3 == div.table.size()
    assert 10 == div.table.tr.size()

    assert 'Components' == div.h2.toString()

    assert 'Controller' == div.h3[0].toString()
    assert 'Repository' == div.h3[1].toString()
    assert 'Service' == div.h3[2].toString()

    assertElements div.table.'*'.td, [
            'org.springframework.samples.petclinic.web.AddOwnerForm', 'JavaBean form controller that is used to add a new Owner to the system.',
            'org.springframework.samples.petclinic.web.AddPetForm', 'JavaBean form controller that is used to add a new Pet to the system.',
            'org.springframework.samples.petclinic.web.AddVisitForm', 'JavaBean form controller that is used to add a new Visit to the system.',
            'org.springframework.samples.petclinic.web.ClinicController', "Annotation-driven MultiActionController that handles all non-form URL's.",
            'org.springframework.samples.petclinic.web.EditOwnerForm', 'JavaBean Form controller that is used to edit an existing Owner.',
            'org.springframework.samples.petclinic.web.EditPetForm', 'JavaBean Form controller that is used to edit an existing Pet.',
            'org.springframework.samples.petclinic.web.FindOwnersForm', 'JavaBean Form controller that is used to search for Owners by last name.',

            'org.springframework.samples.petclinic.hibernate.HibernateClinic', 'Hibernate implementation of the Clinic interface.',
            'org.springframework.samples.petclinic.jpa.EntityManagerClinic', 'JPA implementation of the Clinic interface using EntityManager.',

            'org.springframework.samples.petclinic.jdbc.SimpleJdbcClinic', 'A simple JDBC-based implementation of the {@link Clinic} interface.',
    ]

    assertElements div.table.'*'.td.a.@href, [
            '../apidocs/org/springframework/samples/petclinic/web/AddOwnerForm.html',
            '../apidocs/org/springframework/samples/petclinic/web/AddPetForm.html',
            '../apidocs/org/springframework/samples/petclinic/web/AddVisitForm.html',
            '../apidocs/org/springframework/samples/petclinic/web/ClinicController.html',
            '../apidocs/org/springframework/samples/petclinic/web/EditOwnerForm.html',
            '../apidocs/org/springframework/samples/petclinic/web/EditPetForm.html',
            '../apidocs/org/springframework/samples/petclinic/web/FindOwnersForm.html',

            '../apidocs/org/springframework/samples/petclinic/hibernate/HibernateClinic.html',
            '../apidocs/org/springframework/samples/petclinic/jpa/EntityManagerClinic.html',

            '../apidocs/org/springframework/samples/petclinic/jdbc/SimpleJdbcClinic.html'
    ]
  }

  private def assertMappings(path) {
    def div = path.body.div[1]
    assert 'request-mappings' == div.'@id'.toString()
    assert 1 == div.h2.size()

    assert 'Request Mappings' == div.h2.toString()

    assertElements div.table.'*'.th, ['Method', 'URL Template', 'Class', 'Description']

    assertElements div.table.'*'.td, [
            'GET', '"/"', 'org.springframework.samples.petclinic.web.ClinicController', 'Displays the the welcome view.',
            'GET', '"/owners"', 'org.springframework.samples.petclinic.web.FindOwnersForm', '',
            'GET', '"/owners/*/pets/{petId}/edit"', 'org.springframework.samples.petclinic.web.EditPetForm', 'Displays an edit form for the specified owner.',
            'PUT', '"/owners/*/pets/{petId}/edit"', 'org.springframework.samples.petclinic.web.EditPetForm', 'Processes a pet edit form submission.',
            'POST', '"/owners/*/pets/{petId}/edit"', 'org.springframework.samples.petclinic.web.EditPetForm', 'Processes a pet edit form submission.',
            'DELETE', '"/owners/*/pets/{petId}/edit"', 'org.springframework.samples.petclinic.web.EditPetForm', 'Deletes a pet.',
            'GET', '"/owners/*/pets/{petId}/visits"', 'org.springframework.samples.petclinic.web.ClinicController', 'Displays a list of all visits for a specified pet.',
            'GET', '"/owners/*/pets/{petId}/visits/new"', 'org.springframework.samples.petclinic.web.AddVisitForm', '',
            'POST', '"/owners/*/pets/{petId}/visits/new"', 'org.springframework.samples.petclinic.web.AddVisitForm', '',
            'GET', '"/owners/new"', 'org.springframework.samples.petclinic.web.AddOwnerForm', '',
            'POST', '"/owners/new"', 'org.springframework.samples.petclinic.web.AddOwnerForm', '',
            'GET', '"/owners/search"', 'org.springframework.samples.petclinic.web.FindOwnersForm', '',
            'GET', '"/owners/{ownerId}"', 'org.springframework.samples.petclinic.web.ClinicController', 'Displays the specified owner.',
            'GET', '"/owners/{ownerId}/edit"', 'org.springframework.samples.petclinic.web.EditOwnerForm', 'Displays an edit form for the specified owner.',
            'PUT', '"/owners/{ownerId}/edit"', 'org.springframework.samples.petclinic.web.EditOwnerForm', 'Processes an owner edit form submission.',
            'GET', '"/owners/{ownerId}/pets/new"', 'org.springframework.samples.petclinic.web.AddPetForm', '',
            'POST', '"/owners/{ownerId}/pets/new"', 'org.springframework.samples.petclinic.web.AddPetForm', '',
            'GET', '"/vets"', 'org.springframework.samples.petclinic.web.ClinicController', 'Displays all vets.',
    ]

    assertElements div.table.'*'.td.a.@href, [
            '../apidocs/org/springframework/samples/petclinic/web/ClinicController.html',
            '../apidocs/org/springframework/samples/petclinic/web/FindOwnersForm.html',
            '../apidocs/org/springframework/samples/petclinic/web/EditPetForm.html',
            '../apidocs/org/springframework/samples/petclinic/web/EditPetForm.html',
            '../apidocs/org/springframework/samples/petclinic/web/EditPetForm.html',
            '../apidocs/org/springframework/samples/petclinic/web/EditPetForm.html',
            '../apidocs/org/springframework/samples/petclinic/web/ClinicController.html',
            '../apidocs/org/springframework/samples/petclinic/web/AddVisitForm.html',
            '../apidocs/org/springframework/samples/petclinic/web/AddVisitForm.html',
            '../apidocs/org/springframework/samples/petclinic/web/AddOwnerForm.html',
            '../apidocs/org/springframework/samples/petclinic/web/AddOwnerForm.html',
            '../apidocs/org/springframework/samples/petclinic/web/FindOwnersForm.html',
            '../apidocs/org/springframework/samples/petclinic/web/ClinicController.html',
            '../apidocs/org/springframework/samples/petclinic/web/EditOwnerForm.html',
            '../apidocs/org/springframework/samples/petclinic/web/EditOwnerForm.html',
            '../apidocs/org/springframework/samples/petclinic/web/AddPetForm.html',
            '../apidocs/org/springframework/samples/petclinic/web/AddPetForm.html',
            '../apidocs/org/springframework/samples/petclinic/web/ClinicController.html',
    ]
  }

  def assertElements(element, values) {
    values.eachWithIndex { value, index -> assert value == element[index].toString() }
  }
}