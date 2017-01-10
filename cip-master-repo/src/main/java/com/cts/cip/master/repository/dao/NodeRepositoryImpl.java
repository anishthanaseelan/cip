/*******************************************************************************
 * (C) Copyright 2016 Cognizant Worldwide Limited (fka, CTS UK Ltd) (“CWW”)
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *  
 *   Contributors:
 *       Cognizant Worldwide Limited (fka, CTS UK Ltd) (“CWW”)
 *******************************************************************************/
package com.cts.cip.master.repository.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.cts.cip.common.dto.NodeSearchCriteria;
import com.cts.cip.common.dto.SearchCriteria;
import com.cts.cip.master.repository.entities.Location;
import com.cts.cip.master.repository.entities.Node;

@Repository("nodeRepository")
public class NodeRepositoryImpl implements NodeRepository {

	Logger logger = LoggerFactory.getLogger(NodeRepositoryImpl.class);
	@PersistenceContext
	private EntityManager em;

	@Override
	public String add(Node node) {
		em.persist(node);
		return node.getId();
	}

	@Override
	public void update(Node node) {
		Node existingNode = em.find(Node.class, node.getId());
		if (existingNode != null) {
			em.persist(node);
		}

	}

	@Override
	public void remove(String id) {
		Node node = em.find(Node.class, id);
		if (node != null) {
			node.setActive(false);
			em.persist(node);
		}

	}

	@Override
	public Node findById(String id) {
		return em.find(Node.class, id);
	}

	@Override
	public List<Node> findAll() {
		TypedQuery<Node> query = em.createNamedQuery("Node.findAll", Node.class);
		return query.getResultList();
	}

	@Override
	public int findCount() {
		Query queryTotal = em.createQuery("Select count(n.id) from Node n where n.isActive=1");
		long countResult = (long) queryTotal.getSingleResult();
		return (int) countResult;
	}

	@Override
	public List<Node> findByPage(int pageNumber, int pageSize, String sortField, String sortDirection) {
		logger.debug("Parameters-1 : " + pageNumber + " : " + pageSize + " : " + sortDirection);
		Query query = null;
		if (!(sortDirection.equalsIgnoreCase("asc") || sortDirection.equalsIgnoreCase("desc"))) {			
			query = em.createQuery("From Node order by " + sortField + " " + "asc");
		}
		else{
			query = em.createQuery("From Node order by " + sortField + " " + sortDirection);
		}		
		query.setFirstResult((pageNumber - 1) * pageSize);
		query.setMaxResults(pageSize);
		List<Node> nodeList = query.getResultList();
		return nodeList;
	}

	@Override
	public List<Node> findByCriteria(NodeSearchCriteria nodeSearchCriteria, boolean paginationRequired) {
		logger.debug("Node Search Criteria: " + nodeSearchCriteria + "-" + paginationRequired);
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Node> criteriaQuery = criteriaBuilder.createQuery(Node.class);
		Root<Node> node = criteriaQuery.from(Node.class);
		List<Predicate> predicates = new ArrayList<>();

		if (nodeSearchCriteria.getNodeId() != null && !nodeSearchCriteria.getNodeId().equals("")) {

			predicates.add(criteriaBuilder.like(node.<String> get("id"), nodeSearchCriteria.getNodeId() + "%"));
		}

		if (nodeSearchCriteria.getNodeName() != null && !nodeSearchCriteria.getNodeName().equals("")) {

			predicates.add(criteriaBuilder.like(node.<String> get("name"), nodeSearchCriteria.getNodeName() + "%"));
		}

		if (nodeSearchCriteria.getGln() != null && !nodeSearchCriteria.getGln().equals("")) {
			predicates.add(
					criteriaBuilder.like(node.<String> get("globalLocationNumber"), nodeSearchCriteria.getGln() + "%"));
		}

		criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
		logger.debug("Added the filter criterias to the query");

		SearchCriteria searchCriteria = nodeSearchCriteria.getSearchCriteria();
		if (searchCriteria != null) {
			// Apply the sorting logic
			if (searchCriteria.getSortDirection() != null && searchCriteria.getSortField() != null) {
				String sortField = searchCriteria.getSortField();
				if (sortField.equalsIgnoreCase("name") || sortField.equalsIgnoreCase("id")
						|| sortField.equalsIgnoreCase("gln")) {
					if (sortField.equalsIgnoreCase("gln")) {
						sortField = "globalLocationNumber";
					}
					if (searchCriteria.getSortDirection().equals("asc")) {
						criteriaQuery.orderBy(criteriaBuilder.asc(node.get(sortField)));
					} else if (searchCriteria.getSortDirection().equals("desc")) {
						criteriaQuery.orderBy(criteriaBuilder.desc(node.get(sortField)));
					}
				}
			}
		}
		
		logger.debug("Added the sort criteria to the query");
		Query query = em.createQuery(criteriaQuery);

		if (searchCriteria != null && paginationRequired) {
			query.setFirstResult((searchCriteria.getPageNumber() - 1) * searchCriteria.getPageSize());
			query.setMaxResults(searchCriteria.getPageSize());
		}
		List<Node> nodeList = query.getResultList();
		return nodeList;
	}

	@Override
	public int addLocation(Location location) {
		em.persist(location);
		return location.getId();
	}

	@Override
	public Location findLocationById(int id) {
		return em.find(Location.class, id);
	}

	@Override
	public void updateLocation(Location location) {
		em.persist(location);

	}

}
