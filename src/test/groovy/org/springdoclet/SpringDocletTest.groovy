package org.springdoclet

import com.sun.tools.javadoc.Main
import org.junit.Test
import groovy.util.slurpersupport.GPathResult

class SpringDocletTest {
  @Test
  public void docletInvoked() {
    final String fileName = "spring-doc.html"

    File file = new File("./sample", fileName)
    file.delete()
    assert !file.exists()

    def args = ["-sourcepath", "./sample/src/main/java",
            "-subpackages", "org.springframework.samples.petclinic",
            "-d", "./sample",
            "-f", fileName] as String[]
    Main.execute "docletTest", SpringDoclet.class.name, args

    assert file.exists()

    GPathResult path = new XmlSlurper().parse(file)

    println "Output=[${file.readLines()}]"

    assertPageStructure(path)
    assertComponents(path)
    assertMappings(path)
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
    assert 10 == div.p.size()

    assert 'Components' == div.h2.toString()

    assert 'Controller' == div.h3[0].toString()
    assert 'Repository' == div.h3[1].toString()
    assert 'Service' == div.h3[2].toString()

    assertElements div.p, [
            'org.springframework.samples.petclinic.web.AddOwnerForm',
            'org.springframework.samples.petclinic.web.AddPetForm',
            'org.springframework.samples.petclinic.web.AddVisitForm',
            'org.springframework.samples.petclinic.web.ClinicController',
            'org.springframework.samples.petclinic.web.EditOwnerForm',
            'org.springframework.samples.petclinic.web.EditPetForm',
            'org.springframework.samples.petclinic.web.FindOwnersForm',

            'org.springframework.samples.petclinic.hibernate.HibernateClinic',
            'org.springframework.samples.petclinic.jpa.EntityManagerClinic',

            'org.springframework.samples.petclinic.jdbc.SimpleJdbcClinic'
    ]
  }

  private def assertMappings(path) {
    def div = path.body.div[1]
    assert 'request-mappings' == div.'@id'.toString()
    assert 1 == div.h2.size()

    assert 'Request Mappings' == div.h2.toString()

    assertElements div.table.'*'.td, [
            'GET', '"/"', 'org.springframework.samples.petclinic.web.ClinicController',
            'GET', '"/owners"', 'org.springframework.samples.petclinic.web.FindOwnersForm',
            'GET', '"/owners/*/pets/{petId}/edit"', 'org.springframework.samples.petclinic.web.EditPetForm',
            'PUT', '"/owners/*/pets/{petId}/edit"', 'org.springframework.samples.petclinic.web.EditPetForm',
            'POST', '"/owners/*/pets/{petId}/edit"', 'org.springframework.samples.petclinic.web.EditPetForm',
            'DELETE', '"/owners/*/pets/{petId}/edit"', 'org.springframework.samples.petclinic.web.EditPetForm',
            'GET', '"/owners/*/pets/{petId}/visits"', 'org.springframework.samples.petclinic.web.ClinicController',
            'GET', '"/owners/*/pets/{petId}/visits/new"', 'org.springframework.samples.petclinic.web.AddVisitForm',
            'POST', '"/owners/*/pets/{petId}/visits/new"', 'org.springframework.samples.petclinic.web.AddVisitForm',
            'GET', '"/owners/new"', 'org.springframework.samples.petclinic.web.AddOwnerForm',
            'POST', '"/owners/new"', 'org.springframework.samples.petclinic.web.AddOwnerForm',
            'GET', '"/owners/search"', 'org.springframework.samples.petclinic.web.FindOwnersForm',
            'GET', '"/owners/{ownerId}"', 'org.springframework.samples.petclinic.web.ClinicController',
            'GET', '"/owners/{ownerId}/edit"', 'org.springframework.samples.petclinic.web.EditOwnerForm',
            'PUT', '"/owners/{ownerId}/edit"', 'org.springframework.samples.petclinic.web.EditOwnerForm',
            'GET', '"/owners/{ownerId}/pets/new"', 'org.springframework.samples.petclinic.web.AddPetForm',
            'POST', '"/owners/{ownerId}/pets/new"', 'org.springframework.samples.petclinic.web.AddPetForm',
            'GET', '"/vets"', 'org.springframework.samples.petclinic.web.ClinicController',
    ]
  }

  def assertElements(element, values) {
    values.eachWithIndex { value, index -> assert value == element[index].toString() }
  }
}